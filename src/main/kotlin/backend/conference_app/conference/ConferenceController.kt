package backend.conference_app.conference

import backend.conference_app.error.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
import java.time.format.DateTimeFormatter

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

	@PostMapping("/import/csv")
fun importConferencesFromCsv(
    @RequestParam("file") file: MultipartFile,
    @RequestHeader("Authorization") token: String
): ResponseEntity<Any> {
    return try {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(
                ErrorResponse(400, "File is empty")
            )
        }

        val conferences = parseCsvFile(file)
        if (conferences.isEmpty()) {
            return ResponseEntity.badRequest().body(
                ErrorResponse(400, "No valid conference data found in file")
            )
        }

        val savedConferences = conferenceService.saveAll(conferences)
        ResponseEntity.ok(savedConferences)
    } catch (e: Exception) {
		ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
			ErrorResponse(500, "Error importing conferences: ${e.message}")
		)
	}
}

private fun parseCsvFile(file: MultipartFile): List<Conference> {
    val conferences = mutableListOf<Conference>()
    val dateFormatter = DateTimeFormatter.ISO_DATE // Standard YYYY-MM-DD format

    BufferedReader(InputStreamReader(file.inputStream)).use { reader ->
        var lineNumber = 0
        reader.forEachLine { line ->
            lineNumber++
            try {
                if (lineNumber == 1) return@forEachLine // Skip header

                val values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                    .map { it.trim().removeSurrounding("\"") }

                if (values.size >= 5) {
                    conferences.add(
                        Conference(
                            name = values[0].ifBlank { 
                                throw IllegalArgumentException("Name cannot be blank at line $lineNumber") 
                            },
                            startDate = LocalDate.parse(values[1], dateFormatter),
                            endDate = LocalDate.parse(values[2], dateFormatter),
                            location = values[3].takeIf { it.isNotBlank() },
                            description = values[4].takeIf { it.isNotBlank() }
                        )
                    )
                }
            } catch (e: Exception) {
                throw IllegalArgumentException("Error processing line $lineNumber: ${e.message}")
            }
        }
    }
    return conferences
}

    @GetMapping("/export/csv")
    fun exportConferencesToCsv(): ResponseEntity<String> {
        return try {
            val conferences = conferenceService.getAllConferences()
            val csvData = convertToCsv(conferences)
            ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=conferences_export.csv")
                .body(csvData)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error exporting conferences: ${e.message}"
            )
        }
    }

    private fun convertToCsv(conferences: List<Conference>): String {
        val stringWriter = StringWriter()
        PrintWriter(stringWriter).use { writer ->
            writer.println("Name,Start Date,End Date,Location,Description")
            conferences.forEach { conference ->
                writer.println(
                    "${conference.name}," +
                    "${conference.startDate}," +
                    "${conference.endDate}," +
                    "${conference.location ?: ""}," +
                    "${conference.description ?: ""}"
                )
            }
        }
        return stringWriter.toString()
    }
}