package backend.conference_app.bookmark

import backend.conference_app.event.EventResponse
import java.time.LocalDateTime

data class BookmarkResponse(
	val id: Long,
	val userId: Long,
	val event: EventResponse,
	val createdAt: LocalDateTime
)