package com.traffigorotta.model

enum class ReportPeriod(val label: String) {
    TODAY("Hoje"),
    WEEK("Semana"),
    MONTH("Mês")
}

data class HourlyEarningsSlot(
    val timeRange: String, // ex: "06h - 09h"
    val grossEarnings: Double,
    val completedRides: Int,
    val earningsPerHour: Double,
    val isBestSlot: Boolean = false,
    val isLowestSlot: Boolean = false
)

data class PeriodSummary(
    val period: ReportPeriod,
    val totalGross: Double,
    val totalNet: Double,
    val totalVehicleCost: Double,
    val totalDistanceKm: Double,
    val totalHours: Double,
    val averagePerHour: Double,
    val completedRides: Int,
    val hourlySlots: List<HourlyEarningsSlot>
)
