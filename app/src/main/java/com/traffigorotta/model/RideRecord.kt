package com.traffigorotta.model

/**
 * Registro de uma corrida detectada e processada pelo Rotta.
 *
 * Minimização Estrita (AGENTS.md):
 * - Armazena apenas: valor, distância estimada, tempo estimado, estado, horário e semáforo.
 * - NUNCA armazena: nome, foto, nota ou endereço exato do passageiro.
 */
data class RideRecord(
    val id: String,
    val sourceApp: String,          // "Uber" ou "99"
    val grossValue: Double,         // R$
    val distanceKm: Double,         // km
    val durationMinutes: Int,       // minutos
    val status: RideStatus = RideStatus.COMPLETED,
    val signalGrade: SignalGrade = SignalGrade.GOOD,
    val timestamp: Long = System.currentTimeMillis(),
    val journeyId: String? = null
)

enum class RideStatus(val label: String) {
    OFFERED("Oferecida"),
    ACCEPTED("Aceita"),
    COMPLETED("Concluída"),
    CANCELLED("Cancelada")
}
