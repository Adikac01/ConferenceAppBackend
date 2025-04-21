package backend.conference_app.user

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
	fun findByEmail(email: String): User?
}