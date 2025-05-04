package backend.conference_app.event

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EventRepository : CrudRepository<Event, Long> {

	@Query("SELECT * FROM events WHERE start_time > :startTime")
	fun findByStartTimeAfter(startTime: LocalDateTime): List<Event>

	@Query(
		"""
        SELECT * FROM events 
        WHERE 
            name ILIKE '%' || :sequence || '%' 
            OR description ILIKE '%' || :sequence || '%' 
            OR speaker_info ILIKE '%' || :sequence || '%'
    """
	)
	fun findBySequenceInNameOrDescriptionOrSpeaker(sequence: String): List<Event>
}