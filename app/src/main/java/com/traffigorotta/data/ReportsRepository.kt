package com.traffigorotta.data

import com.traffigorotta.model.HourlyEarningsSlot
import com.traffigorotta.model.PeriodSummary
import com.traffigorotta.model.ReportPeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReportsRepository {

    private val todaySlots = listOf(
        HourlyEarningsSlot("06h - 09h", 174.00, 6, 58.00, isBestSlot = false),
        HourlyEarningsSlot("10h - 13h", 80.00, 3, 26.66, isLowestSlot = true),
        HourlyEarningsSlot("14h - 17h", 111.20, 4, 37.06, isBestSlot = false),
        HourlyEarningsSlot("17h - 20h", 120.00, 5, 40.00, isBestSlot = true)
    )

    private val weekSlots = listOf(
        HourlyEarningsSlot("06h - 09h", 890.00, 32, 59.33, isBestSlot = false),
        HourlyEarningsSlot("10h - 13h", 420.00, 16, 28.00, isLowestSlot = true),
        HourlyEarningsSlot("14h - 17h", 510.00, 21, 34.00, isBestSlot = false),
        HourlyEarningsSlot("17h - 20h", 630.00, 25, 42.00, isBestSlot = true)
    )

    private val monthSlots = listOf(
        HourlyEarningsSlot("06h - 09h", 3560.00, 128, 59.33, isBestSlot = false),
        HourlyEarningsSlot("10h - 13h", 1680.00, 64, 28.00, isLowestSlot = true),
        HourlyEarningsSlot("14h - 17h", 2040.00, 84, 34.00, isBestSlot = false),
        HourlyEarningsSlot("17h - 20h", 2540.00, 106, 42.33, isBestSlot = true)
    )

    private val _todaySummary = MutableStateFlow(
        PeriodSummary(
            period = ReportPeriod.TODAY,
            totalGross = 485.20,
            totalNet = 371.40,
            totalVehicleCost = 113.80,
            totalDistanceKm = 165.0,
            totalHours = 6.2,
            averagePerHour = 78.25,
            completedRides = 18,
            hourlySlots = todaySlots
        )
    )

    private val _weekSummary = MutableStateFlow(
        PeriodSummary(
            period = ReportPeriod.WEEK,
            totalGross = 2450.00,
            totalNet = 1876.00,
            totalVehicleCost = 574.00,
            totalDistanceKm = 832.0,
            totalHours = 32.5,
            averagePerHour = 75.38,
            completedRides = 94,
            hourlySlots = weekSlots
        )
    )

    private val _monthSummary = MutableStateFlow(
        PeriodSummary(
            period = ReportPeriod.MONTH,
            totalGross = 9820.00,
            totalNet = 7520.00,
            totalVehicleCost = 2300.00,
            totalDistanceKm = 3330.0,
            totalHours = 134.0,
            averagePerHour = 73.28,
            completedRides = 382,
            hourlySlots = monthSlots
        )
    )

    fun getSummary(period: ReportPeriod): StateFlow<PeriodSummary> {
        return when (period) {
            ReportPeriod.TODAY -> _todaySummary.asStateFlow()
            ReportPeriod.WEEK -> _weekSummary.asStateFlow()
            ReportPeriod.MONTH -> _monthSummary.asStateFlow()
        }
    }
}
