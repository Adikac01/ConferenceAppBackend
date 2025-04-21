package backend.conference_app.authorization

import backend.conference_app.user.UserRequest
import backend.conference_app.user.UserResponse
import backend.conference_app.user.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

	@PostMapping("/register")
	fun register(@RequestBody request: UserRequest): UserResponse {
		val user = authService.register(request)
		return user.toResponse()
	}

	@PostMapping("/login")
	fun login(@RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
		val user = authService.login(request.email, request.password)
		return if (user != null) {
			ResponseEntity.ok(user.toResponse())
		} else {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		}
	}
}
