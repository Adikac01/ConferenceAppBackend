package backend.conference_app.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtTokenFilter(
	private val jwtService: JwtService,
	@Value("\${jwt.header}") private val header: String,
	@Value("\${jwt.token.prefix}") private val tokenPrefix: String
) : OncePerRequestFilter() {

	@Throws(ServletException::class, IOException::class)
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		val path = request.servletPath

		// Skip JWT processing for public endpoints
		if (path.startsWith("/api/public/")) {
			filterChain.doFilter(request, response)
			return
		}

		val authHeader = request.getHeader(header)

		if (authHeader.isNullOrBlank() || !authHeader.startsWith("$tokenPrefix ")) {
			filterChain.doFilter(request, response)
			return
		}

		val token = authHeader.removePrefix("$tokenPrefix ").trim()

		if (jwtService.validateToken(token)) {
			val claims = jwtService.extractAllClaims(token)
			val userId = claims["userId"].toString().toLong()
			val username = claims.subject
			val authorities = jwtService.getAuthorities(token)

			val principal = JwtUserPrincipal(userId, username, authorities)

			val authentication = UsernamePasswordAuthenticationToken(
				principal,
				null,
				authorities
			).apply {
				details = WebAuthenticationDetailsSource().buildDetails(request)
			}

			SecurityContextHolder.getContext().authentication = authentication
		}

		filterChain.doFilter(request, response)
	}
}