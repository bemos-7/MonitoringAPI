package domain.repository

interface TemperatureRepository {
    fun getCPUTemperature(): Double
    fun getGPUTemperatureNvidia(): Double
}