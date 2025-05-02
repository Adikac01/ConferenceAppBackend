package backend.conference_app.bookmark

import org.springframework.stereotype.Service

@Service
class BookmarkService(private val bookmarkRepository: BookmarkRepository) {

	fun getBookmarksForUser(userId: Long): List<Bookmark> =
		bookmarkRepository.findAllByUserId(userId)

	fun addBookmark(userId: Long, eventId: Long): Bookmark {
		if (!bookmarkRepository.existsByUserIdAndEventId(userId, eventId)) {
			return bookmarkRepository.save(Bookmark(userId = userId, eventId = eventId))
		}
		throw IllegalStateException("Bookmark already exists.")
	}

	fun removeBookmark(userId: Long, eventId: Long) {
		bookmarkRepository.deleteByUserIdAndEventId(userId, eventId)
	}
}