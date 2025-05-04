package backend.conference_app.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException::class)
	fun handleBadRequest(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> =
		ResponseEntity.status(400).body(
			ErrorResponse(400, ex.message ?: "Bad request")
		)

	@ExceptionHandler(IllegalStateException::class)
	fun handleConflict(ex: IllegalStateException): ResponseEntity<ErrorResponse> =
		ResponseEntity.status(409).body(
			ErrorResponse(409, ex.message ?: "Conflict")
		)

	@ExceptionHandler(Exception::class)
	fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> =
		ResponseEntity.status(500).body(
			ErrorResponse(500, "Internal server error: ${ex.localizedMessage}")
		)
}