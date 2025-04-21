package backend.conference_app.conference

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "conferences")
data class Conference(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@field:Column(nullable = false)
	val name: String,

	@field:Column(name = "start_date", nullable = false)
	val startDate: LocalDate,

	@field:Column(name = "end_date", nullable = false)
	val endDate: LocalDate,

	val location: String? = null,
	val description: String? = null
)