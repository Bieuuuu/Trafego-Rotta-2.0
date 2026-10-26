package com.traffigorotta.model

/**
 * Resultado do cálculo determinístico de paridade de combustível do veículo do motorista.
 *
 * Diferencial de mercado:
 * Não utiliza a regra genérica e imprecisa dos 70%.
 * Calcula com base na relação real de consumo (km/l no etanol vs km/l na gasolina)
 * registrada pelo motorista para o seu próprio carro.
 */
data class FuelParityResult(
    val recommendedFuel: FuelType,
    val ethanolCostPerKm: Double,
    val gasolineCostPerKm: Double,
    val realVehicleParityRatio: Double,   // Ponto de equilíbrio real do carro (Etanol km/l / Gasolina km/l)
    val pumpPriceRatio: Double,           // Relação observada na bomba (Preço Etanol / Preço Gasolina)
    val costDifferencePerKm: Double,      // Diferença em R$/km entre os combustíveis
    val estimatedDailySavings: Double,    // Economia estimada no dia (ex: 160 km)
    val estimatedMonthlySavings: Double,  // Economia projetada em 25 dias de trabalho
    val estimatedTankSavings: Double,     // Economia estimada em um tanque típico de 50L
    val generic70PercentMislead: Boolean, // Alerta: a regra cega dos 70% recomendaria o combustível errado!
    val isPracticallyTied: Boolean        // Empate técnico (< 1 centavo por km)
) {
    val recommendedLabel: String
        get() = recommendedFuel.label

    val realParityPercentageFormatted: String
        get() = "%.1f%%".format(realVehicleParityRatio * 100).replace('.', ',')

    val pumpRatioPercentageFormatted: String
        get() = "%.1f%%".format(pumpPriceRatio * 100).replace('.', ',')

    val summaryText: String
        get() = when {
            isPracticallyTied -> "Empate técnico. Tanto etanol quanto gasolina entregam custo por km quase idêntico."
            recommendedFuel == FuelType.ETANOL -> "Etanol compensa mais no seu carro hoje (economia de R$ %.2f no dia / R$ %.2f no mês)".format(estimatedDailySavings, estimatedMonthlySavings)
            else -> "Gasolina compensa mais no seu carro hoje (economia de R$ %.2f no dia / R$ %.2f no mês)".format(estimatedDailySavings, estimatedMonthlySavings)
        }
}

object FuelParityCalculator {

    /**
     * Calcula a paridade exata para o veículo do motorista de forma determinística.
     */
    fun calculate(
        ethanolPrice: Double,
        gasolinePrice: Double,
        ethanolEfficiencyKmPerLiter: Double,
        gasolineEfficiencyKmPerLiter: Double,
        dailyEstimatedKm: Double = 160.0,
        workDaysPerMonth: Int = 25,
        tankCapacityLiters: Double = 50.0
    ): FuelParityResult {
        val safeEthanolEff = if (ethanolEfficiencyKmPerLiter > 0) ethanolEfficiencyKmPerLiter else 9.0
        val safeGasEff = if (gasolineEfficiencyKmPerLiter > 0) gasolineEfficiencyKmPerLiter else 13.0

        val ethanolCostKm = if (safeEthanolEff > 0) ethanolPrice / safeEthanolEff else 0.0
        val gasolineCostKm = if (safeGasEff > 0) gasolinePrice / safeGasEff else 0.0

        val realParityRatio = safeEthanolEff / safeGasEff
        val pumpRatio = if (gasolinePrice > 0) ethanolPrice / gasolinePrice else 1.0

        val diffPerKm = kotlin.math.abs(ethanolCostKm - gasolineCostKm)
        val isTied = diffPerKm < 0.008

        val recommended = when {
            isTied -> FuelType.ETANOL
            ethanolCostKm < gasolineCostKm -> FuelType.ETANOL
            else -> FuelType.GASOLINA
        }

        val dailySavings = diffPerKm * dailyEstimatedKm
        val monthlySavings = dailySavings * workDaysPerMonth

        // Economia por tanque de 50 litros
        val kmPerTank = if (recommended == FuelType.ETANOL) safeEthanolEff * tankCapacityLiters else safeGasEff * tankCapacityLiters
        val tankSavings = diffPerKm * kmPerTank

        // Alerta de armadilha da regra dos 70%:
        // A regra clássica diz: se pumpRatio <= 0.70 -> Etanol, senão Gasolina.
        val naive70Recommendation = if (pumpRatio <= 0.70) FuelType.ETANOL else FuelType.GASOLINA
        val isMisleading = !isTied && (naive70Recommendation != recommended)

        return FuelParityResult(
            recommendedFuel = recommended,
            ethanolCostPerKm = ethanolCostKm,
            gasolineCostPerKm = gasolineCostKm,
            realVehicleParityRatio = realParityRatio,
            pumpPriceRatio = pumpRatio,
            costDifferencePerKm = diffPerKm,
            estimatedDailySavings = dailySavings,
            estimatedMonthlySavings = monthlySavings,
            estimatedTankSavings = tankSavings,
            generic70PercentMislead = isMisleading,
            isPracticallyTied = isTied
        )
    }
}
