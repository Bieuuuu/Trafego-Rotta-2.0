package com.traffigorotta.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FuelParityCalculatorTest {

    @Test
    fun calculate_ethanolMoreAdvantageous_returnsEthanolRecommendation() {
        // Carro: Onix 1.0 (9.5 km/l no Etanol, 13.8 km/l na Gasolina)
        // Bomba: Etanol R$ 3,69, Gasolina R$ 5,89
        val result = FuelParityCalculator.calculate(
            ethanolPrice = 3.69,
            gasolinePrice = 5.89,
            ethanolEfficiencyKmPerLiter = 9.5,
            gasolineEfficiencyKmPerLiter = 13.8,
            dailyEstimatedKm = 180.0
        )

        assertEquals(FuelType.ETANOL, result.recommendedFuel)
        // Etanol: 3.69 / 9.5 = 0.388/km
        // Gasolina: 5.89 / 13.8 = 0.426/km
        assertEquals(0.388, result.ethanolCostPerKm, 0.005)
        assertEquals(0.426, result.gasolineCostPerKm, 0.005)
        assertTrue(result.costDifferencePerKm > 0.03) // Mais de 3 centavos/km de economia
        assertTrue(result.estimatedDailySavings > 6.0) // Mais de R$ 6,00 economizados no dia
        assertFalse(result.isPracticallyTied)
    }

    @Test
    fun calculate_gasolineMoreAdvantageous_returnsGasolineRecommendation() {
        // Bomba com etanol caro: Etanol R$ 4,50, Gasolina R$ 5,89
        val result = FuelParityCalculator.calculate(
            ethanolPrice = 4.50,
            gasolinePrice = 5.89,
            ethanolEfficiencyKmPerLiter = 9.0,
            gasolineEfficiencyKmPerLiter = 13.5
        )

        assertEquals(FuelType.GASOLINA, result.recommendedFuel)
        // Etanol: 4.50 / 9.0 = 0.50/km
        // Gasolina: 5.89 / 13.5 = 0.436/km
        assertTrue(result.gasolineCostPerKm < result.ethanolCostPerKm)
    }

    @Test
    fun calculate_demonstratesFailureOfGeneric70PercentRule() {
        // Carro cuja relação real de consumo é 65% (8.5 km/l etanol vs 13.0 km/l gasolina = 0.6538)
        // Bomba com relação de 68% (Etanol R$ 3,94 / Gasolina R$ 5,79 = 0.680)
        // Pela regra cega dos 70%, o motorista abasteceria etanol.
        // O Rotta calcula com o consumo real e avisa que GASOLINA compensa mais!
        val result = FuelParityCalculator.calculate(
            ethanolPrice = 3.94,
            gasolinePrice = 5.79,
            ethanolEfficiencyKmPerLiter = 8.5,
            gasolineEfficiencyKmPerLiter = 13.0
        )

        // Etanol: 3.94 / 8.5 = 0.4635/km
        // Gasolina: 5.79 / 13.0 = 0.4453/km (Gasolina é mais barata por km!)
        assertEquals(FuelType.GASOLINA, result.recommendedFuel)
        assertTrue(result.gasolineCostPerKm < result.ethanolCostPerKm)
        assertTrue(result.generic70PercentMislead) // Prova documental: regra dos 70% enganaria o motorista
        assertTrue(result.estimatedMonthlySavings > 50.0) // Mais de R$ 50,00 economizados no mês
        assertTrue(result.estimatedTankSavings > 10.0) // Mais de R$ 10,00 por tanque cheio
    }
}
