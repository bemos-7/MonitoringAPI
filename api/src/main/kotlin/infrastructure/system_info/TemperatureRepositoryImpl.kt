package infrastructure.system_info

import domain.repository.TemperatureRepository
import oshi.SystemInfo
import kotlin.math.round

class TemperatureRepositoryImpl : TemperatureRepository {
    override fun getCPUTemperature(): Double {
        val systemInfo = SystemInfo()
        val sensors = systemInfo.hardware.sensors
        return round(sensors.cpuTemperature)
    }

    override fun getGPUTemperatureNvidia(): Double {
        return try {
            val process = Runtime.getRuntime().exec("nvidia-smi --query-gpu=temperature.gpu --format=csv,noheader")
            val output = process.inputStream.bufferedReader().readText().trim()
            output.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }
}