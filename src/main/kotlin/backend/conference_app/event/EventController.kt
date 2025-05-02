package backend.conference_app.event

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

	@GetMapping
	fun getAllEvents(): List<Event> = eventService.getAllEvents()

	@GetMapping("/{id}")
	fun getEventById(@PathVariable id: Long): Event? = eventService.getEventById(id)
}