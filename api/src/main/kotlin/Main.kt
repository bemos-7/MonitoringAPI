import application.use_cases.GetTemperatureUseCase
import domain.model.Temperature
import infrastructure.system_info.TemperatureRepositoryImpl
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import oshi.SystemInfo
import presentation.configs.call_logging.configureCallLogging
import presentation.configs.routing.configureRouting
import presentation.configs.serialization.configureSerialization
import kotlin.math.round

fun main() {
    val temperatureRepository = TemperatureRepositoryImpl()
    val getTemperatureUseCase = GetTemperatureUseCase(temperatureRepository)
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0"
    ) {
        configureCallLogging()
        configureSerialization()
        configureRouting(getTemperatureUseCase)
        println("Server is started!")
    }.start(wait = true)
}