package backend.conference_app.conference

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

	fun saveAll(conferences: List<Conference>): List<Conference> =
		conferenceRepository.saveAll(conferences).toList()
	
	private fun splitCsvLine(line: String): List<String> {
		return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
			.map { it.trim().removeSurrounding("\"") }
	}

	fun parseCsvFile(file: MultipartFile): List<Conference> {
		BufferedReader(InputStreamReader(file.inputStream)).use { reader ->
			return readConferencesFromBufferedReader(reader)
		}
	}

	private fun parseCsvLine(
		line: String,
		lineNumber: Int,
		dateFormatter: DateTimeFormatter
	): Conference? {
		val values = splitCsvLine(line)

		if (values.size < 5) {
			return null
		}

		return Conference(
			name = values[0].ifBlank {
				throw IllegalArgumentException("Name cannot be blank at line $lineNumber")
			},
			startDate = LocalDate.parse(values[1], dateFormatter),
			endDate = LocalDate.parse(values[2], dateFormatter),
			location = values[3].takeIf { it.isNotBlank() },
			description = values[4].takeIf { it.isNotBlank() }
		)
	}

	private fun readConferencesFromBufferedReader(reader: BufferedReader): List<Conference> {
		val conferences = mutableListOf<Conference>()
		val dateFormatter = DateTimeFormatter.ISO_DATE
		var lineNumber = 0

		reader.forEachLine { line ->
			lineNumber++
			if (lineNumber == 1) return@forEachLine // skip header
			try {
				val conference = parseCsvLine(line, lineNumber, dateFormatter)
				if (conference != null) {
					conferences.add(conference)
				}
			} catch (e: Exception) {
				throw IllegalArgumentException("Error processing line $lineNumber: ${e.message}")
			}
		}

		return conferences
	}


	fun convertToCsv(conferences: List<Conference>): String {
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