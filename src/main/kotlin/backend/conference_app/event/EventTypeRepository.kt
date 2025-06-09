package backend.conference_app.event

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventTypeRepository : CrudRepository<EventType, Long>