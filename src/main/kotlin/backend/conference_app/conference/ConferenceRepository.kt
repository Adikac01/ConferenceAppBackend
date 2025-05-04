package backend.conference_app.conference

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ConferenceRepository : CrudRepository<Conference, Long> {

	@Query("SELECT * FROM conferences WHERE start_date > :date")
	fun findByStartDateAfter(date: LocalDate): List<Conference>

	@Query("SELECT * FROM conferences WHERE location = :location")
	fun findByLocation(location: String): List<Conference>
}