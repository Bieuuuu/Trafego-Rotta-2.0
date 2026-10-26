package com.traffigorotta.data

import com.traffigorotta.model.DriverProfile
import com.traffigorotta.model.VehicleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DriverRepository {

    private val _profile = MutableStateFlow(
        DriverProfile(
            name = "Carlos Silva",
            email = "carlos.silva@rotta.app",
            phone = "(11) 98765-4321",
            vehicle = VehicleData(
                model = "Chevrolet Onix 1.0",
                fuelEfficiencyKmPerLiter = 12.8,
                fuelPricePerLiter = 5.89,
                monthlyFixedCosts = 920.0,
                monthlyWorkDays = 26,
                dailyEstimatedKm = 160.0
            ),
            isLoggedIn = true
        )
    )

    val profile: StateFlow<DriverProfile> = _profile.asStateFlow()

    fun login(email: String, name: String = "Carlos Silva") {
        _profile.update {
            it.copy(
                email = email,
                name = name.ifBlank { "Motorista" },
                isLoggedIn = true
            )
        }
    }

    fun logout() {
        _profile.update {
            it.copy(isLoggedIn = false)
        }
    }

    fun updateProfile(
        name: String,
        phone: String,
        vehicle: VehicleData
    ) {
        _profile.update {
            it.copy(
                name = name,
                phone = phone,
                vehicle = vehicle
            )
        }
    }
}
