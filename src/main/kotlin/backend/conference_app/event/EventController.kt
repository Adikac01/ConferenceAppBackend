package backend.conference_app.event

import backend.conference_app.error.ErrorResponse
import backend.conference_app.event.attendence.EventAttendance
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/events")
class EventController(private val service: EventService) {

	@GetMapping
	fun getAllEvents(): ResponseEntity<List<EventResponse>> =
		ResponseEntity.ok(service.getAllEvents())

	@GetMapping("/{id}")
	fun getEventById(@PathVariable id: Long): ResponseEntity<Any> {
		val event = service.getEventById(id)
		return if (event != null) {
			ResponseEntity.ok(event)
		} else {
			ResponseEntity.status(404).body(
				ErrorResponse(404, "Event with id $id not found")
			)
		}
	}

	@GetMapping("/after/{startTime}")
	fun getEventsAfterStartTime(@PathVariable startTime: String): ResponseEntity<List<EventResponse>> {
		val parsedTime = LocalDateTime.parse(startTime)
		return ResponseEntity.ok(service.getEventsAfterStartTime(parsedTime))
	}

	@GetMapping("/search/{sequence}")
	fun searchEvents(@PathVariable sequence: String): ResponseEntity<List<EventResponse>> =
		ResponseEntity.ok(service.searchEvents(sequence.trim()))

	@PostMapping
	fun addEvent(@RequestBody event: Event): ResponseEntity<Event> {
		val created = service.addEvent(event)
		return ResponseEntity.status(201).body(created)
	}

	@PostMapping("/attendance")
	fun addEventAttendance(@RequestBody eventAttendance: EventAttendance): ResponseEntity<EventAttendance> {
		val created = service.addEventAttendance(eventAttendance)
		return ResponseEntity.status(201).body(created)
	}

	@GetMapping("/{eventId}/attendees")
	fun getUsersByEvent(@PathVariable eventId: Long): ResponseEntity<List<EventAttendance>> =
		ResponseEntity.ok(service.getUsersByEvent(eventId))

	@GetMapping("/user/{userId}")
	fun getEventsByUser(@PathVariable userId: Long): ResponseEntity<List<EventResponse>> =
		ResponseEntity.ok(service.getEventsByUser(userId))
}