package backend.conference_app.bookmark

import backend.conference_app.event.EventService
import backend.conference_app.security.SecurityUtils
import org.springframework.stereotype.Service

@Service
class BookmarkService(
	private val bookmarkRepository: BookmarkRepository,
	private val eventService: EventService
) {

	fun getBookmarksForUser(userId: Long): List<BookmarkResponse> {
		SecurityUtils.requireOwnershipOrAdmin(userId)

		val bookmarks = bookmarkRepository.findAllByUserId(userId)
		return bookmarks.mapNotNull { bookmark ->
			val event = eventService.getEventById(bookmark.eventId)
			event?.let {
				BookmarkResponse(
					id = bookmark.id!!,
					userId = bookmark.userId,
					event = it,
					createdAt = bookmark.createdAt
				)
			}
		}
	}

	fun addBookmark(userId: Long, eventId: Long): BookmarkResponse {
		SecurityUtils.requireOwnershipOrAdmin(userId)

		if (!bookmarkRepository.existsByUserIdAndEventId(userId, eventId)) {
			val event = eventService.getEventById(eventId)
				?: throw IllegalArgumentException("Event with ID $eventId not found.")

			val saved = bookmarkRepository.save(Bookmark(userId = userId, eventId = eventId))
			return BookmarkResponse(
				id = saved.id!!,
				userId = saved.userId,
				event = event,
				createdAt = saved.createdAt
			)
		}
		throw IllegalStateException("Bookmark already exists.")
	}

	fun countBookmarksForEvents(): List<EventBookmarkResponse> {
		return bookmarkRepository.findEventBookmarkCounts()
	}

	fun removeBookmark(userId: Long, eventId: Long) {
		SecurityUtils.requireOwnershipOrAdmin(userId)

		bookmarkRepository.deleteByUserIdAndEventId(userId, eventId)
	}
}