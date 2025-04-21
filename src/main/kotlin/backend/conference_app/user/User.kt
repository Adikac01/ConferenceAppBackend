package backend.conference_app.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Entity
@Table("users")
data class User(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	val id: Long? = null,
	@field:Column(nullable = false, unique = true)
	val email: String,
	@field:Column(nullable = false)
	val password: String,

	val name: String? = null,
	val surname: String? = null,

	@field:Column(nullable = false)
	val role: String,

	@field:Column(name = "created_at", nullable = false)
	val createdAt: LocalDateTime = LocalDateTime.now()
)