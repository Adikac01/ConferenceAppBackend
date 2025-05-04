package backend.conference_app.event

import java.time.LocalDateTime

data class EventResponse(
	val id: Long,
	val conferenceId: Long,
	val typeId: Long?,
	val name: String,
	val description: String?,
	val startTime: LocalDateTime,
	val endTime: LocalDateTime,
	val room: String?,
	val remainingPlaces: Int?,  // Computed field
	val speakerInfo: String?
)