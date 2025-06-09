package backend.conference_app.bookmark

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface BookmarkRepository : CrudRepository<Bookmark, Long> {
	fun findAllByUserId(userId: Long): List<Bookmark>
	fun existsByUserIdAndEventId(userId: Long, eventId: Long): Boolean
	fun deleteByUserIdAndEventId(userId: Long, eventId: Long)

	@Query(
		"""
        SELECT 
            e.name AS eventName,
            COUNT(b.id) AS noOfBookmarks,
            e.end_time AS endTime
        FROM bookmarks b
        JOIN events e ON b.event_id = e.id
        GROUP BY e.id, e.name, e.end_time
        ORDER BY noOfBookmarks DESC
    """
	)
	fun findEventBookmarkCounts(): List<EventBookmarkResponse>
}