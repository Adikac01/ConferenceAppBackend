package backend.conference_app.user

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
	@GetMapping
	fun getAllUsers(): Iterable<UserResponse> {
		val users = userService.getAllUsers().map { it.toResponse() }
		println(users)
		return users
	}

	@GetMapping("/{id}")
	fun getUserById(@PathVariable id: Long): UserResponse? = userService.getUserById(id)?.toResponse()

	@PostMapping
	fun createUser(@RequestBody request: UserRequest): UserResponse {
		val user = userService.createUser(request.toEntity())
		return user.toResponse()
	}
}