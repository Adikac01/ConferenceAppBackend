package backend.conference_app.event

import org.springframework.data.repository.CrudRepository

interface EventRepository : CrudRepository<Event, Long>