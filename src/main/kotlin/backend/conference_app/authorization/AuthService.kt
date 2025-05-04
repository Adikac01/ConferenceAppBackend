package backend.conference_app.authorization

import backend.conference_app.email.EmailService
import backend.conference_app.security.JwtService
import backend.conference_app.user.User
import backend.conference_app.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService,
	private val emailService: EmailService
) {

	private val DEFAULT_PASSWORD_LENGTH = 10

	private fun generateRandomPassword(): String {
		val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
		return (1..DEFAULT_PASSWORD_LENGTH)
			.map { chars.random() }
			.joinToString("")
	}

	fun register(request: RegisterRequest): User {
		val generatedPassword = generateRandomPassword()

		val hashedPassword = passwordEncoder.encode(generatedPassword)

		val user = User(
			email = request.email,
			password = hashedPassword,
			name = request.name,
			surname = request.surname,
			role = "attendee",
		)
		val savedUser = userRepository.save(user)

		emailService.sendPasswordEmail(request.email, generatedPassword)

		return savedUser
	}

	fun login(loginRequest: LoginRequest): Pair<User, String>? {
		val user = userRepository.findByEmail(loginRequest.email)
		return if (user != null && passwordEncoder.matches(loginRequest.password, user.password)) {
			val token = jwtService.generateToken(user)
			Pair(user, token)
		} else {
			null
		}
	}

	fun updatePassword(authenticatedEmail: String, request: UpdatePasswordRequest): Boolean {
		val user = userRepository.findByEmail(authenticatedEmail) ?: return false

		return if (passwordEncoder.matches(request.oldPassword, user.password)) {
			val updatedUser = user.copy(password = passwordEncoder.encode(request.newPassword))
			userRepository.save(updatedUser)
			true
		} else {
			false
		}
	}
}