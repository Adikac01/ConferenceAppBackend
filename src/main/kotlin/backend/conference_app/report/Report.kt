package backend.conference_app.report

import backend.conference_app.conference.Conference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reports")
data class Report(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@ManyToOne
	@JoinColumn(name = "conference_id", nullable = false)
	val conference: Conference,

	@field:Column(name = "generated_at", nullable = false)
	val generatedAt: LocalDateTime = LocalDateTime.now(),

	@field:Column(name = "report_data", columnDefinition = "jsonb", nullable = false)
	val reportData: String,

	@field:Column(name = "report_type", nullable = false)
	val reportType: String
)