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
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.round

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0"
    ) {
        configureCallLogging()
        configureSerialization()
        configureRouting()
        println("Server is started!")
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
            println("get temp")
            val cpuTemp = getCPUTemperature()
            val gpuTemp = getNvidiaGpuTemperature()
            val data = TemperatureReading(
                cpu = cpuTemp,
                gpu = gpuTemp
            )
            call.respond(data)
        }
    }
}

private fun getNvidiaGpuTemperature(): Double {
    return try {
        val process = Runtime.getRuntime().exec("nvidia-smi --query-gpu=temperature.gpu --format=csv,noheader")
        val output = process.inputStream.bufferedReader().readText().trim()
        output.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

private fun getCPUTemperature(): Double {
    val systemInfo = SystemInfo()
    val sensors = systemInfo.hardware.sensors
    return round(sensors.cpuTemperature)
}

@Serializable
data class TemperatureReading(
    val cpu: Double,
    val gpu: Double
)