package com.traffigorotta.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.DriverRepository
import com.traffigorotta.model.DriverProfile
import com.traffigorotta.model.FuelType
import com.traffigorotta.model.VehicleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val carModel: String = "",
    val fuelType: FuelType = FuelType.GASOLINA,
    val consumptionInput: String = "12.8",
    val fuelPriceInput: String = "5.89",
    val fixedCostsInput: String = "920",
    val workDaysInput: String = "26",
    val dailyKmInput: String = "160",
    val isLoggedIn: Boolean = false,
    val isEditingCar: Boolean = false,
    val isSavedSuccess: Boolean = false,
    val loginError: String? = null
) {
    // Cálculo determinístico do custo por km derivado dos inputs
    val calculatedFuelCostPerKm: Double
        get() {
            val cons = consumptionInput.replace(',', '.').toDoubleOrNull() ?: 0.0
            val price = fuelPriceInput.replace(',', '.').toDoubleOrNull() ?: 0.0
            return if (cons > 0) price / cons else 0.0
        }

    val calculatedDailyFixedCost: Double
        get() {
            val fixed = fixedCostsInput.replace(',', '.').toDoubleOrNull() ?: 0.0
            val days = workDaysInput.toIntOrNull() ?: 1
            return if (days > 0) fixed / days else 0.0
        }

    val calculatedTotalCostPerKm: Double
        get() {
            val dailyKm = dailyKmInput.replace(',', '.').toDoubleOrNull() ?: 1.0
            val fixedPerKm = if (dailyKm > 0) calculatedDailyFixedCost / dailyKm else 0.0
            return calculatedFuelCostPerKm + fixedPerKm
        }
}

class ProfileViewModel(
    private val repository: DriverRepository = DriverRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.profile.collect { profile ->
                _uiState.update { current ->
                    current.copy(
                        name = profile.name,
                        email = profile.email,
                        phone = profile.phone,
                        carModel = profile.vehicle.model,
                        fuelType = profile.vehicle.fuelType,
                        consumptionInput = "%.1f".format(profile.vehicle.fuelEfficiencyKmPerLiter),
                        fuelPriceInput = "%.2f".format(profile.vehicle.fuelPricePerLiter),
                        fixedCostsInput = "%.0f".format(profile.vehicle.monthlyFixedCosts),
                        workDaysInput = profile.vehicle.monthlyWorkDays.toString(),
                        dailyKmInput = "%.0f".format(profile.vehicle.dailyEstimatedKm),
                        isLoggedIn = profile.isLoggedIn
                    )
                }
            }
        }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value, isSavedSuccess = false) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value, loginError = null) }
    fun onPhoneChange(value: String) = _uiState.update { it.copy(phone = value, isSavedSuccess = false) }
    fun onCarModelChange(value: String) = _uiState.update { it.copy(carModel = value, isSavedSuccess = false) }
    fun onFuelTypeChange(value: FuelType) = _uiState.update { it.copy(fuelType = value, isSavedSuccess = false) }
    fun onConsumptionChange(value: String) = _uiState.update { it.copy(consumptionInput = value, isSavedSuccess = false) }
    fun onFuelPriceChange(value: String) = _uiState.update { it.copy(fuelPriceInput = value, isSavedSuccess = false) }
    fun onFixedCostsChange(value: String) = _uiState.update { it.copy(fixedCostsInput = value, isSavedSuccess = false) }
    fun onWorkDaysChange(value: String) = _uiState.update { it.copy(workDaysInput = value, isSavedSuccess = false) }
    fun onDailyKmChange(value: String) = _uiState.update { it.copy(dailyKmInput = value, isSavedSuccess = false) }

    fun toggleEditCar() = _uiState.update { it.copy(isEditingCar = !it.isEditingCar) }

    fun login(email: String) {
        if (email.isBlank() || !email.contains("@")) {
            _uiState.update { it.copy(loginError = "Digite um e-mail válido") }
            return
        }
        repository.login(email)
    }

    fun logout() {
        repository.logout()
    }

    fun saveProfile() {
        val state = _uiState.value
        val vehicle = VehicleData(
            model = state.carModel.ifBlank { "Carro não informado" },
            fuelType = state.fuelType,
            fuelEfficiencyKmPerLiter = state.consumptionInput.replace(',', '.').toDoubleOrNull() ?: 12.0,
            fuelPricePerLiter = state.fuelPriceInput.replace(',', '.').toDoubleOrNull() ?: 5.80,
            monthlyFixedCosts = state.fixedCostsInput.replace(',', '.').toDoubleOrNull() ?: 800.0,
            monthlyWorkDays = state.workDaysInput.toIntOrNull() ?: 25,
            dailyEstimatedKm = state.dailyKmInput.replace(',', '.').toDoubleOrNull() ?: 150.0
        )
        repository.updateProfile(
            name = state.name.ifBlank { "Motorista" },
            phone = state.phone,
            vehicle = vehicle
        )
        _uiState.update { it.copy(isSavedSuccess = true) }
    }
}
