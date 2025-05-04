package backend.conference_app.conference

import backend.conference_app.error.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/conferences")
class ConferenceController(private val conferenceService: ConferenceService) {

	@GetMapping
	fun getAllConferences(): ResponseEntity<List<Conference>> =
		ResponseEntity.ok(conferenceService.getAllConferences())

	@GetMapping("/{id}")
	fun getConferenceById(@PathVariable id: Long): ResponseEntity<Any> {
		val conf = conferenceService.getConferenceById(id)
		return if (conf != null) {
			ResponseEntity.ok(conf)
		} else {
			ResponseEntity.status(404).body(
				ErrorResponse(404, "Conference with id $id not found")
			)
		}
	}

	@GetMapping("/starting-after/{date}")
	fun getConferencesStartingAfter(@PathVariable date: String): ResponseEntity<List<Conference>> {
		val parsedDate = LocalDate.parse(date)
		return ResponseEntity.ok(conferenceService.getConferencesStartingAfter(parsedDate))
	}

	@GetMapping("/location/{location}")
	fun getConferencesAtLocation(@PathVariable location: String): ResponseEntity<List<Conference>> =
		ResponseEntity.ok(conferenceService.getConferencesAtLocation(location))
}