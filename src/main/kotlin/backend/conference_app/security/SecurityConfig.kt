package backend.conference_app.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableMethodSecurity
@Configuration
class SecurityConfig(
	private val jwtTokenFilter: JwtTokenFilter
) {
	@Bean
	fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		return http
			.cors().and()
			.csrf { it.disable() }
			.authorizeHttpRequests { authz ->
				authz
					.requestMatchers("/api/public/**").permitAll()
					.requestMatchers("/api/protected/**").hasAnyRole("ADMIN", "ATTENDEE")
					.requestMatchers("/api/restricted/**").hasRole("ADMIN")
					.anyRequest().authenticated()
			}
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
	}

	@Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOriginPatterns = listOf("*")  // You can replace "*" with specific origin
        config.allowedHeaders = listOf("*")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}