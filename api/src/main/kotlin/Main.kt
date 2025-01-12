import application.use_cases.GetTemperatureUseCase
import infrastructure.database.config.DatabaseConfig
import infrastructure.database.table.DisplayUi
import infrastructure.system_info.TemperatureRepositoryImpl
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import presentation.configs.call_logging.configureCallLogging
import presentation.configs.routing.configureRouting
import presentation.configs.serialization.configureSerialization

fun main() {
    DatabaseConfig.connect()
    transaction {
        SchemaUtils.create(DisplayUi)
    }
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
    }.start(wait = true)
}