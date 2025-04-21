package backend.conference_app.user

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
	fun createUser(user: User): User = userRepository.save(user)

	fun getAllUsers(): List<User> = userRepository.findAll().toList()

	fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)
}