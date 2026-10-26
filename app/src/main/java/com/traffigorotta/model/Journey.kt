package com.traffigorotta.model

import java.time.LocalDateTime
import java.time.Duration

data class Journey(
    val id: String = "journey_${System.currentTimeMillis()}",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime? = null,
    val grossEarnings: Double = 0.0,
    val completedRidesCount: Int = 0,
    val distanceKm: Double = 0.0,
    val isActive: Boolean = true
) {
    val duration: Duration
        get() {
            val end = endTime ?: LocalDateTime.now()
            return Duration.between(startTime, end)
        }

    val durationHours: Double
        get() = duration.toMinutes().toDouble() / 60.0

    // Ganhos brutos por hora (R$/h)
    val earningsPerHour: Double
        get() = if (durationHours > 0.05) grossEarnings / durationHours else grossEarnings

    // Ganho por km rodado (R$/km)
    val earningsPerKm: Double
        get() = if (distanceKm > 0) grossEarnings / distanceKm else 0.0

    // Lucro líquido estimado com base no custo por km do veículo cadastrado
    fun calculateNetProfit(costPerKm: Double): Double {
        val tripCost = distanceKm * costPerKm
        return grossEarnings - tripCost
    }
}
