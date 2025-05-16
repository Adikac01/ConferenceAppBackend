package backend.conference_app.security

import org.springframework.security.core.GrantedAuthority

data class JwtUserPrincipal(
	val id: Long,
	val email: String,
	val authorities: List<GrantedAuthority>
)
