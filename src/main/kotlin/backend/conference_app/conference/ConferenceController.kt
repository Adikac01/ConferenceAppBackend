package backend.conference_app.conference

import backend.conference_app.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@RequestMapping("/api/restricted/conferences")
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
			ResponseEntity.status(404).body(ErrorResponse(404, "Conference with id $id not found"))
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

	@PostMapping("/import/csv")
	fun importConferencesFromCsv(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
		return try {
			if (file.isEmpty) {
				return ResponseEntity.badRequest().body(ErrorResponse(400, "File is empty"))
			}

			val conferences = conferenceService.parseCsvFile(file)
			if (conferences.isEmpty()) {
				return ResponseEntity.badRequest().body(ErrorResponse(400, "No valid conference data found in file"))
			}

			val savedConferences = conferenceService.saveAll(conferences)
			ResponseEntity.ok(savedConferences)
		} catch (e: Exception) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponse(500, "Error importing conferences: ${e.message}"))
		}
	}

	@GetMapping("/export/csv")
	fun exportConferencesToCsv(): ResponseEntity<String> {
		return try {
			val conferences = conferenceService.getAllConferences()
			val csvData = conferenceService.convertToCsv(conferences)
			ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=conferences_export.csv")
				.body(csvData)
		} catch (e: Exception) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error exporting conferences: ${e.message}")
		}
	}
}