package backend.conference_app.authorization

import backend.conference_app.user.UserResponse

data class AuthResponse(
	val token: String,
	val user: UserResponse? = null
)