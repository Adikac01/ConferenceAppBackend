package backend.conference_app.bookmark

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("bookmarks")
data class Bookmark(
	@Id val id: Long? = null,
	val userId: Long,
	val eventId: Long,
	val createdAt: LocalDateTime = LocalDateTime.now()
)