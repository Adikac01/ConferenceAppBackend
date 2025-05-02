package backend.conference_app.event

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("event_types")
data class EventType(
	@Id val id: Long? = null,
	val name: String,
	val color: String = "#3f51b5",
	val icon: String?
)