package com.traffigorotta.data

import com.traffigorotta.model.Journey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class JourneyRepository {

    // Começa com uma jornada ativa para motorista ver de imediato, ou null
    private val _activeJourney = MutableStateFlow<Journey?>(
        Journey(
            startTime = LocalDateTime.now().minusHours(3).minusMinutes(24),
            grossEarnings = 184.20,
            completedRidesCount = 6,
            distanceKm = 64.5,
            isActive = true
        )
    )
    val activeJourney: StateFlow<Journey?> = _activeJourney.asStateFlow()

    private val _completedJourneys = MutableStateFlow<List<Journey>>(emptyList())
    val completedJourneys: StateFlow<List<Journey>> = _completedJourneys.asStateFlow()

    fun startJourney() {
        if (_activeJourney.value == null) {
            _activeJourney.value = Journey(
                startTime = LocalDateTime.now(),
                grossEarnings = 0.0,
                completedRidesCount = 0,
                distanceKm = 0.0,
                isActive = true
            )
        }
    }

    fun endJourney(): Journey? {
        val current = _activeJourney.value ?: return null
        val finalized = current.copy(
            endTime = LocalDateTime.now(),
            isActive = false
        )
        _completedJourneys.update { listOf(finalized) + it }
        _activeJourney.value = null
        return finalized
    }

    fun addRideToActiveJourney(earnings: Double, km: Double) {
        _activeJourney.update { current ->
            current?.copy(
                grossEarnings = current.grossEarnings + earnings,
                completedRidesCount = current.completedRidesCount + 1,
                distanceKm = current.distanceKm + km
            )
        }
    }
}
