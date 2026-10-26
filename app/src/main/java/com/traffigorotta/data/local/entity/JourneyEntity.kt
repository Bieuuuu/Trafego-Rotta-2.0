package com.traffigorotta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para persistência das jornadas de trabalho do motorista.
 */
@Entity(tableName = "journeys")
data class JourneyEntity(
    @PrimaryKey
    val id: String,
    val startTimeIso: String,
    val endTimeIso: String? = null,
    val grossEarnings: Double = 0.0,
    val distanceKm: Double = 0.0,
    val completedRidesCount: Int = 0,
    val isActive: Boolean = false,
    val activeFuel: String = "ETANOL",
    val fuelCostPerKmApplied: Double = 0.41,
    val totalCostPerKmApplied: Double = 0.69
)
