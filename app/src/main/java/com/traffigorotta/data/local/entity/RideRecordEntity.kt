package com.traffigorotta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para registro das corridas captadas e concluídas.
 * Minimização estrita (LGPD): Nenhum dado pessoal de passageiro é registrado.
 */
@Entity(tableName = "ride_records")
data class RideRecordEntity(
    @PrimaryKey
    val id: String,
    val journeyId: String? = null,
    val sourceApp: String, // "Uber" ou "99"
    val grossValue: Double,
    val distanceKm: Double,
    val durationMinutes: Int,
    val timestamp: Long,
    val status: String, // "OFFERED", "ACCEPTED", "COMPLETED", "CANCELED"
    val signalGrade: String = "GOOD", // "GOOD", "MEDIUM", "BAD"
    val netEstimated: Double = 0.0
)
