package backend.conference_app.user

data class UserResponse(
	val id: Long,
	val email: String,
	val name: String?,
	val surname: String?,
	val role: String,
	val createdAt: String
)
