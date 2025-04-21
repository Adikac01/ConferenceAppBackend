package backend.conference_app.event

import backend.conference_app.conference.Conference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "events")
data class Event(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@ManyToOne
	@JoinColumn(name = "conference_id", nullable = false)
	val conference: Conference,

	@ManyToOne
	@JoinColumn(name = "type_id")
	val type: EventType? = null,

	@field:Column(nullable = false)
	val name: String,

	val description: String? = null,

	@field:Column(name = "start_time", nullable = false)
	val startTime: LocalDateTime,

	@field:Column(name = "end_time", nullable = false)
	val endTime: LocalDateTime,

	val room: String? = null,
	val maxAttendees: Int? = null,

	@field:Column(name = "speaker_info")
	val speakerInfo: String? = null
)
