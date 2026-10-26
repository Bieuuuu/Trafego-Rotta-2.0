package com.traffigorotta.service

import com.traffigorotta.model.RideOffer

/**
 * Tipos de eventos do ciclo de vida das corridas captados pelo AccessibilityService.
 *
 * Limites Inegociáveis (AGENTS.md):
 * 1. O app NUNCA clica, toca, aceita ou cancela corridas de terceiros.
 * 2. O AccessibilityService serve estritamente para LEITURA passiva de dados.
 * 3. Minimização estrita: ignora completamente nomes, fotos, notas e endereços de passageiros.
 * 4. Processamento 100% local no aparelho (< 5 ms).
 */
enum class RideEventType {
    OFFER,        // Nova oferta recebida na tela (calcula semáforo imediatamente)
    ACCEPTED,     // Corrida aceita manualmente pelo motorista (em andamento)
    COMPLETED,    // Viagem concluída (dispara consolidação em relatórios automáticos)
    CANCELED      // Corrida cancelada pelo passageiro ou motorista
}

data class ParsedRideEvent(
    val type: RideEventType,
    val sourceApp: String, // "Uber", "99"
    val grossValue: Double,
    val distanceKm: Double,
    val durationMinutes: Int,
    val rawSummary: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Parser de texto determinístico ultrarrápido para leitura das telas de Uber e 99.
 */
object OfferTextParser {

    // Aceita R$ 38,50, R$ 1.250,00, R$45, R$ 12.80, 38,50 BRL
    private val regexCurrency = Regex("""(?:R\$\s*|BRL\s*)([0-9]{1,3}(?:\.[0-9]{3})*(?:,[0-9]{2})|[0-9]+(?:[.,][0-9]{1,2})?)""", RegexOption.IGNORE_CASE)
    
    // Aceita 12,5 km, 8.2 km, 500 metros, 800m
    private val regexDistanceKm = Regex("""([0-9]+(?:[.,][0-9]+)?)\s*(?:km|quil[ôo]metros)""", RegexOption.IGNORE_CASE)
    private val regexDistanceMeters = Regex("""([0-9]+)\s*(?:m|metros)(?!\w)""", RegexOption.IGNORE_CASE)

    // Aceita 1 h 25 min, 1h30m, 2 horas, 45 min, 18 mins
    private val regexHours = Regex("""([0-9]+)\s*(?:h|hora(?:s)?)""", RegexOption.IGNORE_CASE)
    private val regexMinutes = Regex("""([0-9]+)\s*(?:min(?:uto)?s?|m)(?!\w)""", RegexOption.IGNORE_CASE)

    // Gatilhos de conclusão de viagem (para Relatórios Automáticos)
    private val completionTriggers = listOf(
        "viagem concluída",
        "corrida concluída",
        "você ganhou",
        "ganho da viagem",
        "valor final da viagem",
        "viagem finalizada",
        "corrida finalizada",
        "recebido nesta viagem"
    )

    // Gatilhos de corrida aceita / em andamento
    private val acceptedTriggers = listOf(
        "a caminho do passageiro",
        "dirija até o ponto de embarque",
        "embarque do passageiro",
        "iniciar viagem",
        "deslize para iniciar",
        "desembarque em",
        "a caminho do destino"
    )

    // Gatilhos de cancelamento
    private val canceledTriggers = listOf(
        "viagem cancelada",
        "corrida cancelada",
        "o passageiro cancelou",
        "usuário cancelou a viagem"
    )

    /**
     * Analisa a árvore de texto da janela e classifica o evento da corrida.
     */
    fun parseEvent(
        texts: List<String>,
        packageName: String
    ): ParsedRideEvent? {
        if (texts.isEmpty()) return null
        val combinedText = texts.joinToString(" ")
        val lowerText = combinedText.lowercase()

        val sourceApp = when {
            packageName.contains("ubercab", ignoreCase = true) -> "Uber"
            packageName.contains("taxis99", ignoreCase = true) -> "99"
            combinedText.contains("Uber", ignoreCase = true) -> "Uber"
            combinedText.contains("99", ignoreCase = true) -> "99"
            else -> "App"
        }

        // 1. Verifica se é cancelamento
        if (canceledTriggers.any { lowerText.contains(it) }) {
            return ParsedRideEvent(
                type = RideEventType.CANCELED,
                sourceApp = sourceApp,
                grossValue = 0.0,
                distanceKm = 0.0,
                durationMinutes = 0,
                rawSummary = "Viagem cancelada"
            )
        }

        // 2. Extrai valor monetário (se houver)
        val currencyMatch = regexCurrency.find(combinedText)
        val grossValue = currencyMatch?.groupValues?.get(1)?.let { raw ->
            val normalized = raw.replace(".", "").replace(',', '.')
            normalized.toDoubleOrNull()
        }

        // 3. Extrai distância
        var distanceKm: Double? = null
        val distanceKmMatch = regexDistanceKm.find(combinedText)
        if (distanceKmMatch != null) {
            val rawKm = distanceKmMatch.groupValues[1].replace(',', '.')
            distanceKm = rawKm.toDoubleOrNull()
        } else {
            val metersMatch = regexDistanceMeters.find(combinedText)
            if (metersMatch != null) {
                val meters = metersMatch.groupValues[1].toDoubleOrNull()
                if (meters != null) {
                    distanceKm = meters / 1000.0
                }
            }
        }

        // 4. Extrai tempo estimado
        var totalMinutes = 0
        val hoursMatch = regexHours.find(combinedText)
        if (hoursMatch != null) {
            val h = hoursMatch.groupValues[1].toIntOrNull() ?: 0
            totalMinutes += h * 60
        }
        val minutesMatch = regexMinutes.find(combinedText)
        if (minutesMatch != null) {
            val m = minutesMatch.groupValues[1].toIntOrNull() ?: 0
            totalMinutes += m
        }

        // 5. Verifica se é conclusão de viagem (Relatório Automático)
        if (completionTriggers.any { lowerText.contains(it) } && grossValue != null) {
            return ParsedRideEvent(
                type = RideEventType.COMPLETED,
                sourceApp = sourceApp,
                grossValue = grossValue,
                distanceKm = distanceKm ?: 0.0,
                durationMinutes = if (totalMinutes > 0) totalMinutes else 15,
                rawSummary = "Viagem concluída: R$ %.2f".format(grossValue)
            )
        }

        // 6. Verifica se é corrida aceita / em andamento
        if (acceptedTriggers.any { lowerText.contains(it) }) {
            return ParsedRideEvent(
                type = RideEventType.ACCEPTED,
                sourceApp = sourceApp,
                grossValue = grossValue ?: 0.0,
                distanceKm = distanceKm ?: 0.0,
                durationMinutes = totalMinutes,
                rawSummary = "Em andamento: $sourceApp"
            )
        }

        // 7. Se possui valor monetário e parece ser uma oferta recebida
        if (grossValue != null) {
            return ParsedRideEvent(
                type = RideEventType.OFFER,
                sourceApp = sourceApp,
                grossValue = grossValue,
                distanceKm = distanceKm ?: 1.0,
                durationMinutes = if (totalMinutes > 0) totalMinutes else 5,
                rawSummary = "Oferta $sourceApp: R$ %.2f (%.1f km)".format(grossValue, distanceKm ?: 1.0)
            )
        }

        return null
    }

    /**
     * Mantém compatibilidade direta com chamadas que esperam RideOffer
     */
    fun parseTexts(
        texts: List<String>,
        packageName: String
    ): RideOffer? {
        val event = parseEvent(texts, packageName) ?: return null
        if (event.type != RideEventType.OFFER && event.type != RideEventType.COMPLETED) {
            return null
        }
        return RideOffer(
            id = "offer-${System.currentTimeMillis()}",
            sourceApp = event.sourceApp,
            grossValue = event.grossValue,
            distanceKm = event.distanceKm,
            durationMinutes = event.durationMinutes,
            timestamp = event.timestamp
        )
    }
}
