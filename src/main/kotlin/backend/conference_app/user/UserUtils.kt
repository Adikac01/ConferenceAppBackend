package backend.conference_app.user

fun UserRequest.toEntity(): User {
	return User(
		email = this.email,
		password = this.password,
		name = this.name,
		surname = this.surname,
		role = this.role
	)
}

fun User.toResponse(): UserResponse {
	return UserResponse(
		id = this.id ?: 0,
		email = this.email,
		name = this.name,
		surname = this.surname,
		role = this.role,
		createdAt = this.createdAt.toString()
	)
}