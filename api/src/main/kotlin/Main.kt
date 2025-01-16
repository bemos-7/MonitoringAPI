import application.use_cases.GetTemperatureUseCase
import domain.repository.SelectedDisplayUiRepository
import infrastructure.database.DisplayUiImpl
import infrastructure.database.SelectedDisplayUiImpl
import infrastructure.database.config.DatabaseConfig
import infrastructure.database.table.DisplayUi
import infrastructure.database.table.SelectedDisplayUi
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
        SchemaUtils.create(SelectedDisplayUi)
    }
    val displayUiRepository = DisplayUiImpl()
    val selectedDisplayUiRepository = SelectedDisplayUiImpl()
    val uiDisplay = displayUiRepository.getDisplayUis()
    val selectedDisplay = selectedDisplayUiRepository.getSelectedDisplay()

    if (selectedDisplay == null) {
        uiDisplay.map {
            println("${it.first} -> ${it.second}")
        }
        print("Select the indicators that will be displayed -> ")
        val selectedUi = readLine()!!.toInt()
        selectedDisplayUiRepository.insertSelectedDisplay(selectedUi)
    }

    val temperatureRepository = TemperatureRepositoryImpl()
    val getTemperatureUseCase = GetTemperatureUseCase(
        temperatureRepository,
        selectedDisplayUiRepository
    )
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