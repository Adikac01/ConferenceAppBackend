package backend.conference_app.event

import backend.conference_app.event.attendance.EventAttendance
import backend.conference_app.event.attendance.EventAttendanceRepository
import backend.conference_app.security.SecurityUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventService(
	private val eventRepository: EventRepository,
	private val attendanceRepository: EventAttendanceRepository,
	private val eventTypeRepository: EventTypeRepository
) {

	fun getAllEvents(): List<EventResponse> =
		eventRepository.findAll().map { toEventResponse(it) }

	fun getEventById(id: Long): EventResponse? =
		eventRepository.findById(id).orElse(null)?.let { toEventResponse(it) }

	fun getEventsAfterStartTime(startTime: LocalDateTime): List<EventResponse> =
		eventRepository.findByStartTimeAfter(startTime).map { toEventResponse(it) }

	fun searchEvents(sequence: String): List<EventResponse> {
		return if (sequence.isBlank()) {
			getAllEvents()
		} else {
			eventRepository.findBySequenceInNameOrDescriptionOrSpeaker(sequence)
				.map { toEventResponse(it) }
		}
	}

	fun addEvent(event: Event): Event =
		eventRepository.save(event)

	fun addEventAttendance(eventAttendance: EventAttendance): EventAttendance {
		SecurityUtils.requireOwnershipOrAdmin(eventAttendance.userId)

		val event = eventRepository.findById(eventAttendance.eventId)
			.orElseThrow { IllegalArgumentException("Event with id ${eventAttendance.eventId} not found.") }

		val currentAttendees = attendanceRepository.findByEventId(eventAttendance.eventId).size

		if (event.maxAttendees != null && currentAttendees >= event.maxAttendees) {
			throw IllegalStateException("Event is fully booked. No more attendees can be added.")
		}

		return attendanceRepository.save(eventAttendance)
	}

	fun getUsersByEvent(eventId: Long): List<EventAttendance> =
		attendanceRepository.findByEventId(eventId)

	fun getEventsByUser(userId: Long): List<EventResponse> {
		SecurityUtils.requireOwnershipOrAdmin(userId)

		val attendances = attendanceRepository.findByUserId(userId)
		val eventIds = attendances.map { it.eventId }.toSet()
		return eventRepository.findAll()
			.filter { eventIds.contains(it.id) }
			.map { toEventResponse(it) }
	}

	private fun toEventResponse(event: Event): EventResponse {
		val attendees = attendanceRepository.findByEventId(event.id ?: return event.toDefaultResponse())
		val remaining = event.maxAttendees?.let { it - attendees.size }
		return EventResponse(
			id = event.id,
			conferenceId = event.conferenceId,
			typeId = event.typeId,
			name = event.name,
			description = event.description,
			startTime = event.startTime,
			endTime = event.endTime,
			room = event.room,
			remainingPlaces = remaining,
			speakerInfo = event.speakerInfo
		)
	}

	private fun Event.toDefaultResponse() = EventResponse(
		id = id!!,
		conferenceId = conferenceId,
		typeId = typeId,
		name = name,
		description = description,
		startTime = startTime,
		endTime = endTime,
		room = room,
		remainingPlaces = maxAttendees,
		speakerInfo = speakerInfo
	)

	
}