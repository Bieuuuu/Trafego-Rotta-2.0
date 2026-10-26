package com.traffigorotta.data

import com.traffigorotta.model.RideRecord
import com.traffigorotta.model.RideStatus
import com.traffigorotta.model.SignalGrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RideRepository {

    private val _rides = MutableStateFlow<List<RideRecord>>(defaultSampleRides)
    val rides: StateFlow<List<RideRecord>> = _rides.asStateFlow()

    fun recordRide(ride: RideRecord) {
        _rides.value = listOf(ride) + _rides.value
    }

    fun updateStatus(rideId: String, newStatus: RideStatus) {
        _rides.value = _rides.value.map {
            if (it.id == rideId) it.copy(status = newStatus) else it
        }
    }

    companion object {
        val defaultSampleRides = listOf(
            RideRecord(
                id = "r-1",
                sourceApp = "Uber",
                grossValue = 38.50,
                distanceKm = 12.0,
                durationMinutes = 24,
                status = RideStatus.COMPLETED,
                signalGrade = SignalGrade.GOOD,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 45 // 45 min atrás (faixa manhã)
            ),
            RideRecord(
                id = "r-2",
                sourceApp = "99",
                grossValue = 28.00,
                distanceKm = 8.4,
                durationMinutes = 18,
                status = RideStatus.COMPLETED,
                signalGrade = SignalGrade.GOOD,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 95
            ),
            RideRecord(
                id = "r-3",
                sourceApp = "Uber",
                grossValue = 42.80,
                distanceKm = 14.2,
                durationMinutes = 29,
                status = RideStatus.COMPLETED,
                signalGrade = SignalGrade.GOOD,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 160
            ),
            RideRecord(
                id = "r-4",
                sourceApp = "99",
                grossValue = 19.00,
                distanceKm = 7.5,
                durationMinutes = 28,
                status = RideStatus.COMPLETED,
                signalGrade = SignalGrade.MEDIUM,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 220
            ),
            RideRecord(
                id = "r-5",
                sourceApp = "Uber",
                grossValue = 11.50,
                distanceKm = 6.8,
                durationMinutes = 35,
                status = RideStatus.CANCELLED,
                signalGrade = SignalGrade.BAD,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 300
            )
        )
    }
}
