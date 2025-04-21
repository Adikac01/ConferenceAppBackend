package backend.conference_app.authorization

data class LoginRequest(
	val email: String,
	val password: String
)