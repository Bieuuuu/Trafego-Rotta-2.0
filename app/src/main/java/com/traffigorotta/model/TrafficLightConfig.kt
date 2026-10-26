package com.traffigorotta.model

/**
 * Classificação determinística da corrida calculada pelo copiloto Rotta.
 * Sempre cor + palavra, nunca só cor.
 */
enum class SignalGrade(val label: String) {
    GOOD("Vale"),      // Atende a todos os critérios (Verde #15803D)
    MEDIUM("Neutra"),  // Atende a parte dos critérios (Amarelo #FACC15)
    BAD("Fraca")       // Não atende aos critérios mínimos (Vermelho #B91C1C)
}

/**
 * Configuração dos mínimos que o motorista aceita receber.
 * Valores definidos pelo próprio usuário.
 */
data class TrafficLightConfig(
    val minRatePerKm: Double = 2.20,      // Ex: R$ 2,20 por km
    val minRatePerMinute: Double = 0.60,  // Ex: R$ 0,60 por minuto
    val minRatePerHour: Double = 36.00,   // Ex: R$ 36,00 por hora
    val autoExpandOnOffer: Boolean = true, // Expande overlay automaticamente ao surgir corrida
    val overlayEnabled: Boolean = true,    // Bolha flutuante ligada ou desligada
    val hapticFeedbackOnOffer: Boolean = true, // Vibração tátil sutil: 1 toque firme para Vale, 2 para Neutra
    val soundChimeOnVale: Boolean = false      // Sinal sonoro discreto exclusivo para corrida que Vale
)

/**
 * Dados de uma corrida ofertada pelo app parceiro (Uber / 99).
 * Minimização estrita: Apenas valores operacionais, nunca dados pessoais do passageiro.
 */
data class RideOffer(
    val id: String,
    val sourceApp: String,            // "Uber" ou "99"
    val grossValue: Double,           // Valor oferecido em R$
    val distanceKm: Double,           // Distância estimada total em km
    val durationMinutes: Int,         // Tempo estimado total em minutos
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Resultado da avaliação instantânea (< 1 segundo, no aparelho).
 */
data class SignalEvaluation(
    val offer: RideOffer,
    val ratePerKm: Double,
    val ratePerMin: Double,
    val ratePerHour: Double,
    val meetsKm: Boolean,
    val meetsMin: Boolean,
    val meetsHour: Boolean,
    val grade: SignalGrade
) {
    companion object {
        fun evaluate(offer: RideOffer, config: TrafficLightConfig): SignalEvaluation {
            val ratePerKm = if (offer.distanceKm > 0) offer.grossValue / offer.distanceKm else 0.0
            val ratePerMin = if (offer.durationMinutes > 0) offer.grossValue / offer.durationMinutes else 0.0
            val ratePerHour = ratePerMin * 60.0

            val meetsKm = ratePerKm >= config.minRatePerKm
            val meetsMin = ratePerMin >= config.minRatePerMinute
            val meetsHour = ratePerHour >= config.minRatePerHour

            val passedCount = (if (meetsKm) 1 else 0) + (if (meetsMin) 1 else 0) + (if (meetsHour) 1 else 0)

            val grade = when {
                passedCount == 3 -> SignalGrade.GOOD
                passedCount > 0 -> SignalGrade.MEDIUM
                else -> SignalGrade.BAD
            }

            return SignalEvaluation(
                offer = offer,
                ratePerKm = ratePerKm,
                ratePerMin = ratePerMin,
                ratePerHour = ratePerHour,
                meetsKm = meetsKm,
                meetsMin = meetsMin,
                meetsHour = meetsHour,
                grade = grade
            )
        }
    }
}
