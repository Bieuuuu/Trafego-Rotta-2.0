package com.traffigorotta.ui.signal

import androidx.lifecycle.ViewModel
import com.traffigorotta.data.TrafficLightRepository
import com.traffigorotta.model.RideOffer
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.TrafficLightConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TrafficLightUiState(
    val config: TrafficLightConfig = TrafficLightConfig(),
    val latestEvaluation: SignalEvaluation? = null,
    val isOverlayExpanded: Boolean = false,
    val isOverlayVisible: Boolean = true,
    val isEditingConfig: Boolean = false,
    val testValueInput: String = "28.50",
    val testDistanceInput: String = "8.2",
    val testDurationInput: String = "16",
    val testSourceApp: String = "Uber",
    val simulatedSpeedMs: Long = 180 // Tempo de resposta em ms (< 1 segundo garantido)
)

class TrafficLightViewModel(
    private val repository: TrafficLightRepository = TrafficLightRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrafficLightUiState())
    val uiState: StateFlow<TrafficLightUiState> = _uiState.asStateFlow()

    init {
        // Inicia com uma avaliação prévia calculada
        val initialOffer = TrafficLightRepository.defaultSampleOffers.first()
        val evaluation = repository.evaluateOffer(initialOffer)
        _uiState.value = _uiState.value.copy(
            config = repository.config.value,
            latestEvaluation = evaluation
        )
    }

    fun updateMinRates(minKm: Double, minMin: Double, minHour: Double) {
        val newConfig = _uiState.value.config.copy(
            minRatePerKm = minKm,
            minRatePerMinute = minMin,
            minRatePerHour = minHour
        )
        repository.updateConfig(newConfig)
        _uiState.value = _uiState.value.copy(
            config = newConfig,
            latestEvaluation = repository.latestEvaluation.value,
            isEditingConfig = false
        )
    }

    fun toggleAutoExpand(enabled: Boolean) {
        val newConfig = _uiState.value.config.copy(autoExpandOnOffer = enabled)
        repository.updateConfig(newConfig)
        _uiState.value = _uiState.value.copy(config = newConfig)
    }

    fun toggleOverlayEnabled(enabled: Boolean) {
        val newConfig = _uiState.value.config.copy(overlayEnabled = enabled)
        repository.updateConfig(newConfig)
        _uiState.value = _uiState.value.copy(config = newConfig, isOverlayVisible = enabled)
    }

    fun toggleHapticFeedback(enabled: Boolean) {
        val newConfig = _uiState.value.config.copy(hapticFeedbackOnOffer = enabled)
        repository.updateConfig(newConfig)
        _uiState.value = _uiState.value.copy(config = newConfig)
    }

    fun toggleSoundChime(enabled: Boolean) {
        val newConfig = _uiState.value.config.copy(soundChimeOnVale = enabled)
        repository.updateConfig(newConfig)
        _uiState.value = _uiState.value.copy(config = newConfig)
    }

    fun toggleOverlayExpansion() {
        _uiState.value = _uiState.value.copy(isOverlayExpanded = !_uiState.value.isOverlayExpanded)
    }

    fun setOverlayExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(isOverlayExpanded = expanded)
    }

    fun simulateCustomOffer(
        grossValue: Double,
        distanceKm: Double,
        durationMinutes: Int,
        source: String
    ) {
        val offer = RideOffer(
            id = "custom-${System.currentTimeMillis()}",
            sourceApp = source,
            grossValue = grossValue,
            distanceKm = distanceKm,
            durationMinutes = durationMinutes
        )
        val evaluation = repository.evaluateOffer(offer)
        _uiState.value = _uiState.value.copy(
            latestEvaluation = evaluation,
            isOverlayExpanded = if (_uiState.value.config.autoExpandOnOffer) true else _uiState.value.isOverlayExpanded
        )
    }

    fun simulatePreset(presetIndex: Int) {
        val preset = TrafficLightRepository.defaultSampleOffers.getOrNull(presetIndex)
            ?: TrafficLightRepository.defaultSampleOffers.first()
        val evaluation = repository.evaluateOffer(preset)
        _uiState.value = _uiState.value.copy(
            latestEvaluation = evaluation,
            testValueInput = "%.2f".format(preset.grossValue).replace(',', '.'),
            testDistanceInput = "%.1f".format(preset.distanceKm).replace(',', '.'),
            testDurationInput = preset.durationMinutes.toString(),
            testSourceApp = preset.sourceApp,
            isOverlayExpanded = if (_uiState.value.config.autoExpandOnOffer) true else _uiState.value.isOverlayExpanded
        )
    }
}
