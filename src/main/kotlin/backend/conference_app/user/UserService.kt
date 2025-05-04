package backend.conference_app.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder
) {
	fun createUser(user: User): User {
		val hashedUser = user.copy(password = passwordEncoder.encode(user.password))
		return userRepository.save(hashedUser)
	}

	fun getAllUsers(): List<User> = userRepository.findAll().toList()

	fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

	fun deleteUserById(id: Long): Boolean {
		return if (userRepository.existsById(id)) {
			userRepository.deleteById(id)
			true
		} else {
			false
		}
	}
}