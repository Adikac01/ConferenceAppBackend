package backend.conference_app.event

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("events")
data class Event(
	@Id val id: Long? = null,
	val conferenceId: Long,
	val typeId: Long?,
	val name: String,
	val description: String?,
	val startTime: LocalDateTime,
	val endTime: LocalDateTime,
	val room: String?,
	val maxAttendees: Int?,
	val speakerInfo: String?
)