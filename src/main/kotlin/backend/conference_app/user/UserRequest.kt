package backend.conference_app.user

data class UserRequest(
	val email: String,
	val password: String,
	val name: String?,
	val surname: String?,
	val role: String
)
