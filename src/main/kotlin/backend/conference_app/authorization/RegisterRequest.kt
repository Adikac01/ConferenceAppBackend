package backend.conference_app.authorization

data class RegisterRequest(
	val email: String,
	val name: String?,
	val surname: String?
)