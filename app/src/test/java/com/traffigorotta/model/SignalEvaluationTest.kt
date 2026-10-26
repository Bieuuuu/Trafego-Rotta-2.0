package com.traffigorotta.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SignalEvaluationTest {

    private val defaultConfig = TrafficLightConfig(
        minRatePerKm = 2.20,
        minRatePerMinute = 0.60,
        minRatePerHour = 36.00
    )

    @Test
    fun evaluateRide_allCriteriaMet_returnsGoodVale() {
        // R$ 30,00 por 10 km em 20 min -> R$ 3,00/km (>= 2.20), R$ 1,50/min (>= 0.60), R$ 90/h (>= 36.00)
        val offer = RideOffer(
            id = "uber-1",
            sourceApp = "Uber",
            grossValue = 30.00,
            distanceKm = 10.0,
            durationMinutes = 20
        )

        val evaluation = SignalEvaluation.evaluate(offer, defaultConfig)

        assertEquals(SignalGrade.GOOD, evaluation.grade)
        assertEquals("Vale", evaluation.grade.label)
        assertTrue(evaluation.meetsKm)
        assertTrue(evaluation.meetsMin)
        assertTrue(evaluation.meetsHour)
        assertEquals(3.00, evaluation.ratePerKm, 0.01)
        assertEquals(1.50, evaluation.ratePerMin, 0.01)
        assertEquals(90.00, evaluation.ratePerHour, 0.01)
    }

    @Test
    fun evaluateRide_partialCriteriaMet_returnsMediumNeutra() {
        // R$ 25,00 por 15 km em 25 min -> R$ 1,67/km (< 2.20), R$ 1,00/min (>= 0.60), R$ 60/h (>= 36.00)
        val offer = RideOffer(
            id = "99-1",
            sourceApp = "99",
            grossValue = 25.00,
            distanceKm = 15.0,
            durationMinutes = 25
        )

        val evaluation = SignalEvaluation.evaluate(offer, defaultConfig)

        assertEquals(SignalGrade.MEDIUM, evaluation.grade)
        assertEquals("Neutra", evaluation.grade.label)
        assertFalse(evaluation.meetsKm)
        assertTrue(evaluation.meetsMin)
        assertTrue(evaluation.meetsHour)
    }

    @Test
    fun evaluateRide_noCriteriaMet_returnsBadFraca() {
        // R$ 12,00 por 12 km em 35 min -> R$ 1,00/km (< 2.20), R$ 0,34/min (< 0.60), R$ 20,57/h (< 36.00)
        val offer = RideOffer(
            id = "uber-bad",
            sourceApp = "Uber",
            grossValue = 12.00,
            distanceKm = 12.0,
            durationMinutes = 35
        )

        val evaluation = SignalEvaluation.evaluate(offer, defaultConfig)

        assertEquals(SignalGrade.BAD, evaluation.grade)
        assertEquals("Fraca", evaluation.grade.label)
        assertFalse(evaluation.meetsKm)
        assertFalse(evaluation.meetsMin)
        assertFalse(evaluation.meetsHour)
    }

    @Test
    fun evaluateRide_zeroDistanceOrTime_handlesSafelyWithoutCrashing() {
        val zeroOffer = RideOffer(
            id = "edge-1",
            sourceApp = "Uber",
            grossValue = 10.0,
            distanceKm = 0.0,
            durationMinutes = 0
        )

        val evaluation = SignalEvaluation.evaluate(zeroOffer, defaultConfig)

        assertEquals(SignalGrade.BAD, evaluation.grade)
        assertEquals(0.0, evaluation.ratePerKm, 0.001)
        assertEquals(0.0, evaluation.ratePerMin, 0.001)
        assertEquals(0.0, evaluation.ratePerHour, 0.001)
    }
}
