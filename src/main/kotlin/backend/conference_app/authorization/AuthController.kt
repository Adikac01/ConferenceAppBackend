package backend.conference_app.authorization

import backend.conference_app.user.UserResponse
import backend.conference_app.user.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {

	@PostMapping("/public/auth/register")
	fun register(@RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
		val user = authService.register(request)
		return ResponseEntity.status(201).body(user.toResponse())
	}

	@PostMapping("/public/auth/login")
	fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
		val result = authService.login(request)
		return if (result != null) {
			ResponseEntity.ok(
				AuthResponse(
					token = result.second,
					user = result.first.toResponse()
				)
			)
		} else {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		}
	}

	@PutMapping("/protected/auth/update-password")
	fun updatePassword(
		@RequestBody request: UpdatePasswordRequest,
		authentication: Authentication
	): ResponseEntity<Void> {
		val email = authentication.name
		val success = authService.updatePassword(email, request)
		return if (success) {
			ResponseEntity.ok().build()
		} else {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		}
	}
}
