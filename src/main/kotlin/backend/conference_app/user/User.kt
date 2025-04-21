package backend.conference_app.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
	@Id val id: Long? = null,
	val email: String,
	val password: String,
	val name: String?,
	val surname: String?,
	val role: String,
	val createdAt: LocalDateTime = LocalDateTime.now()
)