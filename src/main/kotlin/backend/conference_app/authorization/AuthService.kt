package backend.conference_app.authorization

import backend.conference_app.user.User
import backend.conference_app.user.UserRepository
import backend.conference_app.user.UserRequest
import backend.conference_app.user.toEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService
) {

	fun register(request: UserRequest): User {
		val hashedUser = request.toEntity().copy(
			password = passwordEncoder.encode(request.password)
		)
		return userRepository.save(hashedUser)
	}

	fun login(email: String, password: String): Pair<User, String>? {
		val user = userRepository.findByEmail(email)
		return if (user != null && passwordEncoder.matches(password, user.password)) {
			val token = jwtService.generateToken(user)
			Pair(user, token)
		} else {
			null
		}
	}
}