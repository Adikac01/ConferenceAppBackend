package backend.conference_app.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfig(
	private val jwtTokenFilter: JwtTokenFilter
) {
	@Bean
	fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		return http
			.csrf { it.disable() }
			.authorizeHttpRequests { authz ->
				authz
					.requestMatchers("/api/public/**").permitAll()
					.requestMatchers("/api/users/**").hasRole("ADMIN")
					.anyRequest().authenticated()
			}
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
	}
}