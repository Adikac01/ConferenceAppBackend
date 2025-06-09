package backend.conference_app.bookmark

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BookmarkReportRepository(
	private val jdbcTemplate: JdbcTemplate
) {
	fun findEventBookmarkCounts(): List<EventBookmarkResponse> {
		val sql = """
            SELECT 
                e.name AS eventName,
                COUNT(b.id) AS noOfBookmarks,
                e.end_time AS endTime
            FROM bookmarks b
            JOIN events e ON b.event_id = e.id
            GROUP BY e.id, e.name, e.end_time
            ORDER BY noOfBookmarks DESC
        """.trimIndent()

		return jdbcTemplate.query(sql) { rs, _ ->
			EventBookmarkResponse(
				eventName = rs.getString("eventName"),
				noOfBookmarks = rs.getLong("noOfBookmarks"),
				endTime = rs.getTimestamp("endTime").toLocalDateTime()
			)
		}
	}
}