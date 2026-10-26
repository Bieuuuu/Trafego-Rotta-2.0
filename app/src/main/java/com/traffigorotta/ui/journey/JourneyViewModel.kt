package com.traffigorotta.ui.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.DriverRepository
import com.traffigorotta.data.JourneyRepository
import com.traffigorotta.model.Journey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

import com.traffigorotta.model.FuelParityCalculator
import com.traffigorotta.model.FuelParityResult
import com.traffigorotta.model.FuelType

data class JourneyUiState(
    val activeJourney: Journey? = null,
    val isJourneyActive: Boolean = false,
    val elapsedHours: Long = 0,
    val elapsedMinutes: Long = 0,
    val formattedGrossEarnings: String = "R$ 0,00",
    val vehicleCostPerKm: Double = 0.69,
    val estimatedVehicleCost: Double = 0.0,
    val estimatedNetProfit: Double = 0.0,
    val showEndConfirmation: Boolean = false,
    val lastCompletedJourney: Journey? = null,
    val ethanolPrice: Double = 3.79,
    val gasolinePrice: Double = 5.69,
    val ethanolEfficiencyKmPerLiter: Double = 9.2,
    val gasolineEfficiencyKmPerLiter: Double = 13.5,
    val activeFuel: FuelType = FuelType.ETANOL,
    val fuelParity: FuelParityResult = FuelParityCalculator.calculate(3.79, 5.69, 9.2, 13.5),
    val isEditingFuelPrices: Boolean = false
) {
    val durationFormatted: String
        get() = "${elapsedHours}h ${elapsedMinutes}m"
}

class JourneyViewModel(
    val journeyRepository: JourneyRepository = JourneyRepository(),
    private val driverRepository: DriverRepository = DriverRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyUiState())
    val uiState: StateFlow<JourneyUiState> = _uiState.asStateFlow()

    init {
        // Observar custo por km do veículo do motorista
        viewModelScope.launch {
            driverRepository.profile.collect { profile ->
                val costKm = profile.vehicle.totalCostPerKm
                _uiState.update { it.copy(vehicleCostPerKm = if (costKm > 0) costKm else 0.69) }
                recalculateMetrics()
            }
        }

        // Observar jornada ativa
        viewModelScope.launch {
            journeyRepository.activeJourney.collect { journey ->
                _uiState.update { current ->
                    current.copy(
                        activeJourney = journey,
                        isJourneyActive = journey != null && journey.isActive
                    )
                }
                recalculateMetrics()
            }
        }

        // Timer para atualizar o tempo transcorrido a cada 10 segundos
        viewModelScope.launch {
            while (true) {
                val journey = _uiState.value.activeJourney
                if (journey != null && journey.isActive) {
                    val duration = Duration.between(journey.startTime, LocalDateTime.now())
                    val hours = duration.toHours()
                    val minutes = duration.toMinutes() % 60
                    _uiState.update {
                        it.copy(
                            elapsedHours = hours,
                            elapsedMinutes = minutes
                        )
                    }
                }
                delay(10_000)
            }
        }
    }

    private fun recalculateMetrics() {
        val journey = _uiState.value.activeJourney
        val costPerKm = _uiState.value.vehicleCostPerKm

        if (journey != null) {
            val duration = Duration.between(journey.startTime, LocalDateTime.now())
            val vehicleCost = journey.distanceKm * costPerKm
            val net = journey.grossEarnings - vehicleCost

            _uiState.update {
                it.copy(
                    elapsedHours = duration.toHours(),
                    elapsedMinutes = duration.toMinutes() % 60,
                    formattedGrossEarnings = "R$ %.2f".format(journey.grossEarnings).replace('.', ','),
                    estimatedVehicleCost = vehicleCost,
                    estimatedNetProfit = net
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    elapsedHours = 0,
                    elapsedMinutes = 0,
                    formattedGrossEarnings = "R$ 0,00",
                    estimatedVehicleCost = 0.0,
                    estimatedNetProfit = 0.0
                )
            }
        }
    }

    fun startJourney() {
        journeyRepository.startJourney()
    }

    fun requestEndJourney() {
        _uiState.update { it.copy(showEndConfirmation = true) }
    }

    fun cancelEndJourney() {
        _uiState.update { it.copy(showEndConfirmation = false) }
    }

    fun confirmEndJourney() {
        val finalized = journeyRepository.endJourney()
        _uiState.update {
            it.copy(
                showEndConfirmation = false,
                lastCompletedJourney = finalized
            )
        }
    }

    fun addSimulatedRide(value: Double, km: Double) {
        journeyRepository.addRideToActiveJourney(value, km)
        recalculateMetrics()
    }

    fun updateFuelPrices(ethanol: Double, gasoline: Double) {
        val current = _uiState.value
        val parity = FuelParityCalculator.calculate(
            ethanolPrice = ethanol,
            gasolinePrice = gasoline,
            ethanolEfficiencyKmPerLiter = current.ethanolEfficiencyKmPerLiter,
            gasolineEfficiencyKmPerLiter = current.gasolineEfficiencyKmPerLiter
        )
        val activeFuelCost = if (current.activeFuel == FuelType.ETANOL) parity.ethanolCostPerKm else parity.gasolineCostPerKm
        val fixedCost = 0.28
        val newVehicleCostKm = activeFuelCost + fixedCost

        _uiState.update {
            it.copy(
                ethanolPrice = ethanol,
                gasolinePrice = gasoline,
                fuelParity = parity,
                vehicleCostPerKm = newVehicleCostKm,
                isEditingFuelPrices = false
            )
        }
        recalculateMetrics()
    }

    fun selectActiveFuel(fuel: FuelType) {
        val current = _uiState.value
        val parity = current.fuelParity
        val activeFuelCost = if (fuel == FuelType.ETANOL) parity.ethanolCostPerKm else parity.gasolineCostPerKm
        val fixedCost = 0.28
        val newVehicleCostKm = activeFuelCost + fixedCost

        _uiState.update {
            it.copy(
                activeFuel = fuel,
                vehicleCostPerKm = newVehicleCostKm
            )
        }
        recalculateMetrics()
    }

    fun toggleEditingFuelPrices() {
        _uiState.update { it.copy(isEditingFuelPrices = !it.isEditingFuelPrices) }
    }
}
