package backend.conference_app.event.attendence

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventAttendanceRepository : CrudRepository<EventAttendance, Long> {

	@Query("SELECT * FROM event_attendance WHERE event_id = :eventId")
	fun findByEventId(eventId: Long): List<EventAttendance>

	@Query("SELECT * FROM event_attendance WHERE user_id = :userId")
	fun findByUserId(userId: Long): List<EventAttendance>
}