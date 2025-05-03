package backend.conference_app.authorization

import backend.conference_app.user.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService(
	@Value("\${jwt.secret}")
	private val secretKey: String
) {
	private val signInKey: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

	fun createToken(claims: Map<String, Any>, subject: String): String {
		val now = Date()
		val expiryDate = Date(now.time + 1000 * 60 * 60)  // 1 hour

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(signInKey, SignatureAlgorithm.HS256)
			.compact()
	}

	fun extractUsername(token: String): String =
		extractAllClaims(token).subject

	fun validateToken(token: String): Boolean = try {
		extractAllClaims(token)
		true
	} catch (e: Exception) {
		println("User not authorized")
		false
	}

	fun generateToken(user: User): String {
		val claims = mapOf(
			"role" to user.role
		)
		return createToken(claims, user.email)
	}

	fun getAuthorities(token: String): List<GrantedAuthority> {
		val claims = extractAllClaims(token)
		val role = claims["role"].toString().uppercase()
		return listOf(SimpleGrantedAuthority("ROLE_${role}"))
	}

	private fun extractAllClaims(token: String): Claims =
		Jwts.parserBuilder()
			.setSigningKey(signInKey)
			.build()
			.parseClaimsJws(token)
			.body
}