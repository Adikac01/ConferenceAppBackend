package backend.conference_app.email

import org.springframework.scheduling.annotation.Async

interface EmailService {
	@Async
	fun sendPasswordEmail(to: String, password: String)
}