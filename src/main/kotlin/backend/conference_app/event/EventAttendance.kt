package backend.conference_app.event

import backend.conference_app.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event_attendance", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "event_id"])])
data class EventAttendance(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	val user: User,

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	val event: Event,

	val attended: Boolean = false,

	@field:Column(name = "check_in_time")
	val checkInTime: LocalDateTime? = null
)
