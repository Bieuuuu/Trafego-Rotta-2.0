package com.traffigorotta.data

import com.traffigorotta.model.RideOffer
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.TrafficLightConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrafficLightRepository {
    private val _config = MutableStateFlow(
        TrafficLightConfig(
            minRatePerKm = 2.20,
            minRatePerMinute = 0.60,
            minRatePerHour = 36.00,
            autoExpandOnOffer = true,
            overlayEnabled = true
        )
    )
    val config: StateFlow<TrafficLightConfig> = _config.asStateFlow()

    private val _latestEvaluation = MutableStateFlow<SignalEvaluation?>(null)
    val latestEvaluation: StateFlow<SignalEvaluation?> = _latestEvaluation.asStateFlow()

    fun updateConfig(newConfig: TrafficLightConfig) {
        _config.value = newConfig
        // Reavalia a última oferta com a nova configuração se houver
        _latestEvaluation.value?.let { current ->
            _latestEvaluation.value = SignalEvaluation.evaluate(current.offer, newConfig)
        }
    }

    fun evaluateOffer(offer: RideOffer): SignalEvaluation {
        val result = SignalEvaluation.evaluate(offer, _config.value)
        _latestEvaluation.value = result
        return result
    }

    fun clearEvaluation() {
        _latestEvaluation.value = null
    }

    companion object {
        val defaultSampleOffers = listOf(
            RideOffer(
                id = "sample-1",
                sourceApp = "Uber",
                grossValue = 28.50,
                distanceKm = 8.2,
                durationMinutes = 16
            ),
            RideOffer(
                id = "sample-2",
                sourceApp = "99",
                grossValue = 18.00,
                distanceKm = 10.5,
                durationMinutes = 25
            ),
            RideOffer(
                id = "sample-3",
                sourceApp = "Uber",
                grossValue = 12.00,
                distanceKm = 8.0,
                durationMinutes = 22
            )
        )
    }
}
