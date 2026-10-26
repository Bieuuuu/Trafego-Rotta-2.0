package com.traffigorotta.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RecommendationEngineTest {

    @Test
    fun generateRecommendations_returnsStructuredDirectAdvice() {
        val rides = listOf(
            RideRecord(
                id = "r1",
                sourceApp = "Uber",
                grossValue = 42.0,
                distanceKm = 14.0,
                durationMinutes = 25,
                netEstimated = 32.34,
                status = RideStatus.COMPLETED
            )
        )

        val recommendations = RecommendationEngine.generate(
            rides = rides,
            costPerKm = 0.69,
            dailyGoal = 350.0,
            currentDailyGross = 210.0
        )

        assertTrue(recommendations.isNotEmpty())
        val peakRec = recommendations.find { it.category == "MELHOR HORÁRIO" }
        val costRec = recommendations.find { it.category == "CUSTO POR KM" }
        val goalRec = recommendations.find { it.category == "META DIÁRIA" }
        val fuelRec = recommendations.find { it.category == "COMBUSTÍVEL IDEAL" }

        assertNotNull(peakRec)
        assertNotNull(costRec)
        assertNotNull(goalRec)
        assertNotNull(fuelRec)
        assertEquals("Faltam R$ 140,00 para bater a meta", goalRec?.title)
    }

    @Test
    fun generateRecommendations_fuelParity_recommendsEthanolWhenCheaper() {
        // Etanol 3,79 (9,2 km/l = 0,41/km) vs Gasolina 5,69 (13,5 km/l = 0,42/km) -> Etanol vence
        val recommendations = RecommendationEngine.generate(
            rides = emptyList(),
            costPerKm = 0.69,
            dailyGoal = 500.0,
            currentDailyGross = 300.0,
            ethanolPrice = 3.79,
            gasolinePrice = 5.69,
            ethanolEfficiency = 9.2,
            gasolineEfficiency = 13.5
        )

        val fuelRec = recommendations.find { it.category == "COMBUSTÍVEL IDEAL" }
        assertNotNull(fuelRec)
        assertEquals("Etanol é mais vantajoso hoje", fuelRec?.title)
        assertTrue(fuelRec?.dominantStat?.contains("R$") == true)
    }

    @Test
    fun generateRecommendations_fuelParity_recommendsGasolineAndWarns70Rule() {
        // Bomba a 68,7% (3,50 / 5,10), mas carro faz 8 km/l no etanol vs 13 km/l na gasolina (61,5% real)
        val recommendations = RecommendationEngine.generate(
            rides = emptyList(),
            costPerKm = 0.69,
            dailyGoal = 500.0,
            currentDailyGross = 300.0,
            ethanolPrice = 3.50,
            gasolinePrice = 5.10,
            ethanolEfficiency = 8.0,
            gasolineEfficiency = 13.0
        )

        val fuelRec = recommendations.find { it.category == "COMBUSTÍVEL IDEAL" }
        assertNotNull(fuelRec)
        assertEquals("Gasolina rende mais por km", fuelRec?.title)
        assertTrue(fuelRec?.description?.contains("regra dos 70%") == true)
    }

    @Test
    fun generateRecommendations_goalCompleted_showsGoalDone() {
        val recommendations = RecommendationEngine.generate(
            rides = emptyList(),
            costPerKm = 0.69,
            dailyGoal = 400.0,
            currentDailyGross = 485.20
        )

        val goalRec = recommendations.find { it.category == "META DIÁRIA" }
        assertNotNull(goalRec)
        assertEquals("Meta batida com folga hoje", goalRec?.title)
        assertEquals("100%", goalRec?.dominantStat)
    }
}
