package backend.conference_app.bookmark

import org.springframework.data.repository.CrudRepository

interface BookmarkRepository : CrudRepository<Bookmark, Long> {
	fun findAllByUserId(userId: Long): List<Bookmark>
	fun existsByUserIdAndEventId(userId: Long, eventId: Long): Boolean
	fun deleteByUserIdAndEventId(userId: Long, eventId: Long)
}