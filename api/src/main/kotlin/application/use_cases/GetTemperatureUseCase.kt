package application.use_cases

import domain.model.Temperature
import domain.repository.TemperatureRepository

class GetTemperatureUseCase(
    private val temperatureRepository: TemperatureRepository
) {
    fun execute(): Temperature {
        return Temperature(
            cpu = temperatureRepository.getCPUTemperature(),
            gpu = temperatureRepository.getCPUTemperature()
        )
    }
}