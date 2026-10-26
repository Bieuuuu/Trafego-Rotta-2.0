package com.traffigorotta

import com.traffigorotta.model.RideOffer
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.SignalGrade
import com.traffigorotta.model.TrafficLightConfig
import com.traffigorotta.service.OfferTextParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Suíte de Testes para Cenários Extremos do Copiloto Rotta.
 *
 * Cobre:
 * 1. Viagens longas com duração em horas e minutos ("1 h 25 min" vs "25 min").
 * 2. Viagens curtíssimas com trânsito travado (alto R$/km, péssimo R$/h).
 * 3. Formatações de moeda complexas (valores com milhar, ex: R$ 1.250,00).
 * 4. Distâncias informadas em metros (ex: 800m).
 * 5. Corridas "isca" com longa distância até o passageiro.
 * 6. Casos de borda com valores zerados ou quase nulos (divisão segura).
 */
class ExtremeScenariosTest {

    private val config = TrafficLightConfig(
        minRatePerKm = 2.20,
        minRatePerMinute = 0.60,
        minRatePerHour = 36.00
    )

    @Test
    fun parseIntermunicipalTrip_withHoursAndMinutes_correctlyCalculatesTotalMinutes() {
        // Exemplo real: Viagem Campinas -> SP
        val texts = listOf(
            "UberX VIP",
            "R$ 215,80",
            "92,5 km",
            "1 h 35 min de viagem",
            "Pedágio estimado incluso"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.ubercab.driver")

        assertNotNull(offer)
        assertEquals(215.80, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(92.5, offer?.distanceKm ?: 0.0, 0.01)
        // 1h 35min = 95 minutos (e NÃO 35 minutos!)
        assertEquals(95, offer?.durationMinutes)

        // Avaliação no semáforo:
        // R$/km = 215.80 / 92.5 = 2.33 (>= 2.20 OK)
        // R$/min = 215.80 / 95 = 2.27 (>= 0.60 OK)
        // R$/h = 2.27 * 60 = 136.29 (>= 36.00 OK)
        val evaluation = SignalEvaluation.evaluate(offer!!, config)
        assertEquals(SignalGrade.GOOD, evaluation.grade)
        assertEquals("Vale", evaluation.grade.label)
    }

    @Test
    fun parseShortTripInExtremeTraffic_failsHourlyRateCriteria() {
        // Exemplo real: 1,5 km na Av. Paulista em horário de pico demorando 25 minutos
        val texts = listOf(
            "99Pop",
            "R$ 9,50",
            "1,5 km",
            "25 mins"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.taxis99.driver")

        assertNotNull(offer)
        assertEquals(9.50, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(1.5, offer?.distanceKm ?: 0.0, 0.01)
        assertEquals(25, offer?.durationMinutes)

        val eval = SignalEvaluation.evaluate(offer!!, config)

        // R$/km = 9.50 / 1.5 = R$ 6.33/km (Passa com folga)
        assertTrue(eval.meetsKm)
        // R$/min = 9.50 / 25 = R$ 0.38/min (< 0.60 REPROVA)
        assertFalse(eval.meetsMin)
        // R$/h = R$ 22.80/h (< 36.00 REPROVA)
        assertFalse(eval.meetsHour)

        // Resultado esperado: NEUTRA (Amarelo) - alerta motorista contra trânsito travado
        assertEquals(SignalGrade.MEDIUM, eval.grade)
        assertEquals("Neutra", eval.grade.label)
    }

    @Test
    fun parseDistanceInMeters_convertsToFractionalKm() {
        val texts = listOf(
            "UberX",
            "R$ 8,00",
            "800 metros",
            "4 min"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.ubercab.driver")

        assertNotNull(offer)
        assertEquals(8.00, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(0.8, offer?.distanceKm ?: 0.0, 0.01)
        assertEquals(4, offer?.durationMinutes)

        val eval = SignalEvaluation.evaluate(offer!!, config)
        assertEquals(SignalGrade.GOOD, eval.grade)
        assertEquals("Vale", eval.grade.label)
    }

    @Test
    fun parseHighValueThousandSeparator_extractsCorrectGrossNumber() {
        val texts = listOf(
            "Corrida Especial",
            "R$ 1.150,00",
            "380,0 km",
            "4 h 20 min"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.ubercab.driver")

        assertNotNull(offer)
        assertEquals(1150.00, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(380.0, offer?.distanceKm ?: 0.0, 0.01)
        assertEquals(260, offer?.durationMinutes)
    }

    @Test
    fun evaluateTrappedBaitRide_reprovesAllCriteria() {
        // Corrida "furada": 22 km total por apenas R$ 23,00 em 48 min
        val offer = RideOffer(
            id = "bait-1",
            sourceApp = "Uber",
            grossValue = 23.00,
            distanceKm = 22.0,
            durationMinutes = 48
        )

        val eval = SignalEvaluation.evaluate(offer, config)

        // R$/km = 23.00 / 22.0 = 1.04 (< 2.20)
        assertFalse(eval.meetsKm)
        // R$/min = 23.00 / 48 = 0.48 (< 0.60)
        assertFalse(eval.meetsMin)
        // R$/h = 28.75 (< 36.00)
        assertFalse(eval.meetsHour)

        // Resultado esperado: FRACA (Vermelho)
        assertEquals(SignalGrade.BAD, eval.grade)
        assertEquals("Fraca", eval.grade.label)
    }

    @Test
    fun evaluateDivisionByZeroDefense_handlesCleanly() {
        val offerZeroKm = RideOffer(
            id = "zero-km",
            sourceApp = "99",
            grossValue = 15.00,
            distanceKm = 0.0,
            durationMinutes = 0
        )

        val eval = SignalEvaluation.evaluate(offerZeroKm, config)

        assertEquals(0.0, eval.ratePerKm, 0.001)
        assertEquals(0.0, eval.ratePerMin, 0.001)
        assertEquals(0.0, eval.ratePerHour, 0.001)
        assertEquals(SignalGrade.BAD, eval.grade)
    }
}
