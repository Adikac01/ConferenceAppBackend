package backend.conference_app.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {

	fun requireOwnershipOrAdmin(requestedUserId: Long) {
		val authentication = SecurityContextHolder.getContext().authentication
		val principal = authentication.principal as? JwtUserPrincipal
			?: throw AccessDeniedException("Invalid authentication principal")

		val isAdmin = principal.authorities.any { it.authority == "ROLE_ADMIN" }

		if (!isAdmin && principal.id != requestedUserId) {
			throw AccessDeniedException("User ${principal.id} is not allowed to access user $requestedUserId's data")
		}
	}
}