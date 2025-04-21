package backend.conference_app.bookmark

import backend.conference_app.event.Event
import backend.conference_app.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "bookmarks", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "event_id"])])
data class Bookmark(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	val user: User,

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	val event: Event,

	@field:Column(name = "created_at", nullable = false)
	val createdAt: LocalDateTime = LocalDateTime.now()
)
