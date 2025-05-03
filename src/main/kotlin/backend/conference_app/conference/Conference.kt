package backend.conference_app.conference

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("conferences")
data class Conference(
	@Id val id: Long? = null,
	val name: String,
	val startDate: LocalDate,
	val endDate: LocalDate,
	val location: String?,
	val description: String?
)