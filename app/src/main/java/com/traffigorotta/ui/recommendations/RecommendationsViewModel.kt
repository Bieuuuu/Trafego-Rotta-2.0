package com.traffigorotta.ui.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.DriverRepository
import com.traffigorotta.data.GoalsRepository
import com.traffigorotta.data.RideRepository
import com.traffigorotta.model.DriverRecommendation
import com.traffigorotta.model.RecommendationEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecommendationsUiState(
    val recommendations: List<DriverRecommendation> = emptyList(),
    val costPerKm: Double = 0.69,
    val dailyGoal: Double = 500.0,
    val currentDailyGross: Double = 485.20,
    val ethanolPrice: Double = 3.79,
    val gasolinePrice: Double = 5.69,
    val ethanolEfficiency: Double = 9.2,
    val gasolineEfficiency: Double = 13.5
)

class RecommendationsViewModel(
    private val rideRepository: RideRepository = RideRepository(),
    private val driverRepository: DriverRepository = DriverRepository(),
    private val goalsRepository: GoalsRepository = GoalsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecommendationsUiState())
    val uiState: StateFlow<RecommendationsUiState> = _uiState.asStateFlow()

    init {
        // Observa alterações no perfil do veículo
        viewModelScope.launch {
            driverRepository.profile.collect { profile ->
                val costKm = profile.vehicle.totalCostPerKm
                _uiState.update {
                    it.copy(
                        costPerKm = if (costKm > 0) costKm else 0.69,
                        gasolinePrice = if (profile.vehicle.fuelPricePerLiter > 0) profile.vehicle.fuelPricePerLiter else 5.69,
                        gasolineEfficiency = if (profile.vehicle.fuelEfficiencyKmPerLiter > 0) profile.vehicle.fuelEfficiencyKmPerLiter else 13.5
                    )
                }
                loadRecommendations()
            }
        }

        // Observa alterações na meta diária
        viewModelScope.launch {
            goalsRepository.goals.collect { goals ->
                _uiState.update { it.copy(dailyGoal = goals.dailyGrossGoal) }
                loadRecommendations()
            }
        }

        // Observa histórico de corridas
        viewModelScope.launch {
            rideRepository.rides.collect {
                loadRecommendations()
            }
        }
    }

    fun loadRecommendations() {
        val state = _uiState.value
        val rides = rideRepository.rides.value
        val list = RecommendationEngine.generate(
            rides = rides,
            costPerKm = state.costPerKm,
            dailyGoal = state.dailyGoal,
            currentDailyGross = state.currentDailyGross,
            ethanolPrice = state.ethanolPrice,
            gasolinePrice = state.gasolinePrice,
            ethanolEfficiency = state.ethanolEfficiency,
            gasolineEfficiency = state.gasolineEfficiency
        )
        _uiState.update { it.copy(recommendations = list) }
    }
}
