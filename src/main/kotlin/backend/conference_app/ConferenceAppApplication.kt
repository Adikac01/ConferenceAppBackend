package backend.conference_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class ConferenceAppApplication

fun main(args: Array<String>) {
	runApplication<ConferenceAppApplication>(*args)
}
