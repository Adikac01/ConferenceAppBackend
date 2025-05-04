package backend.conference_app.event.attendence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("event_attendance")
data class EventAttendance(
	@Id val id: Long? = null,
	val userId: Long,
	val eventId: Long,
	val attended: Boolean = false,
	val checkInTime: LocalDateTime?
)