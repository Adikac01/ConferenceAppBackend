package backend.conference_app.authorization

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key

@Service
class JwtService(
	@Value("\${jwt.secret}")
	private val secretKey: String
) {

	fun extractUsername(token: String): String =
		extractAllClaims(token).subject

	fun validateToken(token: String): Boolean = try {
		extractAllClaims(token)
		true
	} catch (e: Exception) {
		false
	}

	fun getAuthorities(token: String): List<GrantedAuthority> {
		val claims = extractAllClaims(token)
		val roles = claims["roles"] as? List<*> ?: emptyList<Any>()
		return roles.mapNotNull { role ->
			role?.toString()?.let { SimpleGrantedAuthority(it) }
		}
	}

	private fun extractAllClaims(token: String): Claims =
		Jwts.parserBuilder()
			.setSigningKey(signInKey)
			.build()
			.parseClaimsJws(token)
			.body

	private val signInKey: Key
		get() = Keys.hmacShaKeyFor(secretKey.toByteArray())
}