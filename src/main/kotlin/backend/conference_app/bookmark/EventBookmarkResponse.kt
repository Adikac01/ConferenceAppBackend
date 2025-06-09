package backend.conference_app.bookmark

import java.time.LocalDateTime

data class EventBookmarkResponse(
	val eventName: String,
	val noOfBookmarks: Short,
	val endTime: LocalDateTime
)