package backend.conference_app.user

import backend.conference_app.error.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

	@GetMapping
	fun getAllUsers(): ResponseEntity<List<UserResponse>> {
		val users = userService.getAllUsers().map { it.toResponse() }
		return ResponseEntity.ok(users)
	}

	@GetMapping("/{id}")
	fun getUserById(@PathVariable id: Long): ResponseEntity<Any> {
		val user = userService.getUserById(id)?.toResponse()
		return if (user != null) {
			ResponseEntity.ok(user)
		} else {
			ResponseEntity.status(404).body(
				ErrorResponse(404, "User with id $id not found")
			)
		}
	}

	@PostMapping
	fun createUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
		val user = userService.createUser(request.toEntity())
		return ResponseEntity.status(201).body(user.toResponse())
	}
}