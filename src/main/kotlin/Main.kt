import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.slf4j.event.Level
import oshi.SystemInfo

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0"
    ) {
        configureCallLogging()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}

fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Welcome to the MonitoringService")
        }
        get("/api/temperature") {
            val gpuTemp = getNvidiaGpuTemperature()
            val data = TemperatureReading(
                temperature = gpuTemp
            )
            call.respond(data)
        }
    }
}

fun getNvidiaGpuTemperature(): Double {
    return try {
        val process = Runtime.getRuntime().exec("nvidia-smi --query-gpu=temperature.gpu --format=csv,noheader")
        val output = process.inputStream.bufferedReader().readText().trim()
        output.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

@Serializable
data class TemperatureReading(
    val temperature: Double,
)