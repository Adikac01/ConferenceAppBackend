package backend.conference_app.conference

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConferenceService(private val conferenceRepository: ConferenceRepository) {

	fun getAllConferences(): List<Conference> =
		conferenceRepository.findAll().toList()

	fun getConferenceById(id: Long): Conference? =
		conferenceRepository.findById(id).orElse(null)

	fun getConferencesStartingAfter(date: LocalDate): List<Conference> =
		conferenceRepository.findByStartDateAfter(date)

	fun getConferencesAtLocation(location: String): List<Conference> =
		conferenceRepository.findByLocation(location)
}