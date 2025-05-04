package backend.conference_app.email

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailServiceImpl(
	private val mailSender: JavaMailSender,
	private val templateEngine: TemplateEngine
) : EmailService {

	override fun sendPasswordEmail(to: String, password: String) {
		val context = Context()
		context.setVariable("password", password)

		val htmlBody = templateEngine.process("email-template", context)

		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message, true)

		helper.setTo(to)
		helper.setSubject("Your Account Password")
		helper.setText(htmlBody, true)

		mailSender.send(message)
	}
}