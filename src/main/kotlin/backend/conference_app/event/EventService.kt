package backend.conference_app.event

import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository: EventRepository) {

	fun getAllEvents(): List<Event> = eventRepository.findAll().toList()

	fun getEventById(id: Long): Event? = eventRepository.findById(id).orElse(null)
}