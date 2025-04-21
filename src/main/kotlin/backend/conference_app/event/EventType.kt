package backend.conference_app.event

import jakarta.persistence.*

@Entity
@Table(name = "event_types")
data class EventType(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@field:Column(nullable = false)
	val name: String,

	val color: String = "#3f51b5",
	val icon: String? = null
)