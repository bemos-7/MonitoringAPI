package presentation.configs.routing

import application.use_cases.GetTemperatureUseCase
import domain.model.Temperature
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    getTemperatureUseCase: GetTemperatureUseCase
) {
    routing {
        get("/") {
            call.respondText("Welcome to the MonitoringService")
        }
        get("/api/temperature") {
            val temp = getTemperatureUseCase.execute()
            call.respond(temp)
        }
    }
}