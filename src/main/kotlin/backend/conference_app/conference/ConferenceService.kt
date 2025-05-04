package backend.conference_app.conference

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConferenceService(private val repository: ConferenceRepository) {

	fun getAllConferences(): List<Conference> =
		repository.findAll().toList()

	fun getConferenceById(id: Long): Conference? =
		repository.findById(id).orElse(null)

	fun getConferencesStartingAfter(date: LocalDate): List<Conference> =
		repository.findByStartDateAfter(date)

	fun getConferencesAtLocation(location: String): List<Conference> =
		repository.findByLocation(location)
}