package backend.conference_app.user

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
	@GetMapping
	fun getAllUsers(): Iterable<UserResponse> = userService.getAllUsers().map { it.toResponse() }

	@GetMapping("/{id}")
	fun getUserById(@PathVariable id: Long): UserResponse? = userService.getUserById(id)?.toResponse()

	@PostMapping
	fun createUser(@RequestBody request: UserRequest): UserResponse {
		val user = userService.createUser(request.toEntity())
		return user.toResponse()
	}
}