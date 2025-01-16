package application.use_cases

import domain.model.Temperature
import domain.repository.SelectedDisplayUiRepository
import domain.repository.TemperatureRepository

class GetTemperatureUseCase(
    private val temperatureRepository: TemperatureRepository,
    private val selectedDisplayUiRepository: SelectedDisplayUiRepository
) {
    fun execute(): Temperature {
        return Temperature(
            cpu = temperatureRepository.getCPUTemperature(),
            gpu = temperatureRepository.getGPUTemperatureNvidia(),
            ui = selectedDisplayUiRepository.getSelectedDisplay()
        )
    }
}