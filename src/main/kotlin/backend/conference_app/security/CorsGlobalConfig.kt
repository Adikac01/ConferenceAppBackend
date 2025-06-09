// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.web.cors.CorsConfiguration
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource
// import org.springframework.web.filter.CorsFilter


// @Configuration
// class CorsGlobalConfig {

//     @Bean
//     fun corsFilter(): CorsFilter {
//         val config = CorsConfiguration()
//         config.allowCredentials = true
//         config.allowedOrigins = listOf("http://localhost:4200", "http://192.168.0.27:4200")
//         config.allowedHeaders = listOf("*")
//         config.allowedMethods = listOf("*")

//         val source = UrlBasedCorsConfigurationSource()
//         source.registerCorsConfiguration("/**", config)
//         return CorsFilter(source)
//     }
// }