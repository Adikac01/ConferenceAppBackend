package backend.conference_app.error

data class ErrorResponse(
	val errorCode: Int,
	val message: String
)