package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Temperature(
    val cpu: Double,
    val gpu: Double
)