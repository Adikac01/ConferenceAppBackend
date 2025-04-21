package backend.conference_app.user

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
	@GetMapping
	fun getAllUsers(): Iterable<User> = userService.getAllUsers()

	@GetMapping("/{id}")
	fun getUserById(@PathVariable id: Long): User? = userService.getUserById(id)

	@PostMapping
	fun createUser(@RequestBody user: User): User = userService.createUser(user)
}