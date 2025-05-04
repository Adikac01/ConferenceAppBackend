package backend.conference_app.email

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class ThymeleafEmailConfig {

	@Bean
	fun emailTemplateEngine(): TemplateEngine {
		val templateResolver = ClassLoaderTemplateResolver().apply {
			prefix = "templates/"
			suffix = ".html"
			setTemplateMode("HTML")
			characterEncoding = "UTF-8"
		}

		return TemplateEngine().apply {
			setTemplateResolver(templateResolver)
		}
	}
}