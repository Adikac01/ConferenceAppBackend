package backend.conference_app.authorization

data class UpdatePasswordRequest(
	val oldPassword: String,
	val newPassword: String
)
