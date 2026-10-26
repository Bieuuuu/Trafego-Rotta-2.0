package com.traffigorotta.model

import java.time.Instant
import java.time.ZoneId

/**
 * Recomendações acionáveis e determinísticas para o motorista (Fase 3 - AGENTS.md).
 * Conteúdo em português do Brasil, números claros e sem jargões.
 * Processamento 100% no aparelho, sem IA nem dependências externas.
 */
data class DriverRecommendation(
    val id: String,
    val category: String,           // Ex: "MELHOR HORÁRIO", "CUSTO POR KM", "META DIÁRIA", "COMBUSTÍVEL IDEAL"
    val title: String,              // Ex: "06h às 09h é seu melhor horário"
    val description: String,        // Explicação concisa e contextualizada
    val dominantStat: String? = null, // Número em destaque
    val dominantLabel: String? = null // Rótulo em caixa alta
)

object RecommendationEngine {

    fun generate(
        rides: List<RideRecord>,
        costPerKm: Double,
        dailyGoal: Double,
        currentDailyGross: Double,
        ethanolPrice: Double = 3.79,
        gasolinePrice: Double = 5.69,
        ethanolEfficiency: Double = 9.2,
        gasolineEfficiency: Double = 13.5
    ): List<DriverRecommendation> {
        val completedRides = rides.filter { it.status == RideStatus.COMPLETED }
        val recommendations = mutableListOf<DriverRecommendation>()

        // 1. Recomendação de Horário Mais Lucrativo (analisa histórico real de corridas)
        val bestSlot = calculateBestTimeSlot(completedRides)
        recommendations.add(
            DriverRecommendation(
                id = "rec-hours",
                category = "MELHOR HORÁRIO",
                title = "Faixa das ${bestSlot.slotLabel} rende mais",
                description = "Seu histórico registra média de faturamento de ${bestSlot.formattedRatePerHour} nessa faixa. Priorize conectar nesse intervalo para atingir sua meta mais cedo.",
                dominantStat = bestSlot.formattedRatePerHour,
                dominantLabel = "MÉDIA NA ${bestSlot.periodName.uppercase()}"
            )
        )

        // 2. Recomendação de Paridade Real de Combustível (Motor Determinístico)
        val parity = FuelParityCalculator.calculate(
            ethanolPrice = ethanolPrice,
            gasolinePrice = gasolinePrice,
            ethanolEfficiencyKmPerLiter = ethanolEfficiency,
            gasolineEfficiencyKmPerLiter = gasolineEfficiency
        )

        if (parity.recommendedFuel == FuelType.ETANOL) {
            val tankSavings = parity.savingsPerKm * ethanolEfficiency * 50
            val formattedTankSavings = "+ R$ %.2f".format(tankSavings).replace('.', ',')
            recommendations.add(
                DriverRecommendation(
                    id = "rec-fuel",
                    category = "COMBUSTÍVEL IDEAL",
                    title = "Etanol é mais vantajoso hoje",
                    description = "O Etanol custa R$ %.2f/km contra R$ %.2f/km na Gasolina no seu carro. Encher o tanque no Etanol gera economia de até %s.".format(
                        parity.costPerKmEthanol,
                        parity.costPerKmGasoline,
                        formattedTankSavings
                    ),
                    dominantStat = formattedTankSavings,
                    dominantLabel = "ECONOMIA / TANQUE (50L)"
                )
            )
        } else {
            val formattedCostGas = "R$ %.2f/km".format(parity.costPerKmGasoline).replace('.', ',')
            val alert70Text = if (parity.isSevenPercentRuleMisleading) {
                "Atenção: a bomba está a %.1f%% (abaixo de 70%%), mas pela paridade real do seu veículo (%.1f%%) a Gasolina rende mais por km.".format(
                    parity.pumpParityPercentage,
                    parity.realVehicleParityPercentage
                )
            } else {
                "A Gasolina custa R$ %.2f/km vs R$ %.2f/km no Etanol.".format(
                    parity.costPerKmGasoline,
                    parity.costPerKmEthanol
                )
            }
            recommendations.add(
                DriverRecommendation(
                    id = "rec-fuel",
                    category = "COMBUSTÍVEL IDEAL",
                    title = "Gasolina rende mais por km",
                    description = alert70Text,
                    dominantStat = formattedCostGas,
                    dominantLabel = "CUSTO GASOLINA / KM"
                )
            )
        }

        // 3. Recomendação de Custo Operacional Real por Km
        val formattedCost = "R$ %.2f".format(costPerKm).replace('.', ',')
        recommendations.add(
            DriverRecommendation(
                id = "rec-cost",
                category = "CUSTO POR KM",
                title = "Seu custo real é $formattedCost por km",
                description = "Somando combustível e custos fixos proporcionais (depreciação, seguro e IPVA). Recuse ou filtre ofertas com valor/km muito próximo ao seu custo.",
                dominantStat = formattedCost,
                dominantLabel = "CUSTO REAL / KM"
            )
        )

        // 4. Recomendação de Ritmo da Meta
        val remaining = dailyGoal - currentDailyGross
        if (remaining > 0) {
            val formattedRem = "R$ %.2f".format(remaining).replace('.', ',')
            val avgRideValue = if (completedRides.isNotEmpty()) {
                completedRides.map { it.grossValue }.average()
            } else {
                28.0
            }
            val ridesNeeded = kotlin.math.max(1, kotlin.math.ceil(remaining / avgRideValue).toInt())

            recommendations.add(
                DriverRecommendation(
                    id = "rec-goal",
                    category = "META DIÁRIA",
                    title = "Faltam $formattedRem para bater a meta",
                    description = "Com a sua média atual de R$ %.2f por corrida, mais %d %s completam o faturamento planejado para hoje.".format(
                        avgRideValue,
                        ridesNeeded,
                        if (ridesNeeded == 1) "corrida fecha" else "corridas fecham"
                    ),
                    dominantStat = formattedRem,
                    dominantLabel = "RESTANTE HOJE"
                )
            )
        } else {
            recommendations.add(
                DriverRecommendation(
                    id = "rec-goal-done",
                    category = "META DIÁRIA",
                    title = "Meta batida com folga hoje",
                    description = "Você atingiu 100% da meta diária estipulada. Cada nova corrida agora é lucro líquido adicional.",
                    dominantStat = "100%",
                    dominantLabel = "META CONCLUÍDA"
                )
            )
        }

        // 5. Eficiência do Semáforo (Adesão ao Sinal Verde)
        val goodCount = completedRides.count { it.signalGrade == SignalGrade.GOOD }
        val totalCount = completedRides.size
        val efficiencyPct = if (totalCount > 0) (goodCount * 100) / totalCount else 85

        recommendations.add(
            DriverRecommendation(
                id = "rec-semaphore",
                category = "FILTRO DO SEMÁFORO",
                title = "$efficiencyPct% das corridas foram 'Vale'",
                description = "Manter o rigor de aceitar preferencialmente corridas com sinal verde mantém seu lucro por hora no teto máximo.",
                dominantStat = "$efficiencyPct%",
                dominantLabel = "TAXA DE CORRIDAS BOAS"
            )
        )

        return recommendations
    }

    private data class SlotResult(
        val slotLabel: String,
        val periodName: String,
        val formattedRatePerHour: String
    )

    private fun calculateBestTimeSlot(rides: List<RideRecord>): SlotResult {
        if (rides.isEmpty()) {
            return SlotResult("06h às 09h", "manhã", "R$ 58,00/h")
        }

        // Mapeia corridas por faixas de horário
        val slots = mapOf(
            "06h às 09h" to Pair("manhã", 6..9),
            "10h às 13h" to Pair("almoço", 10..13),
            "14h às 17h" to Pair("tarde", 14..17),
            "17h às 20h" to Pair("noite", 17..20)
        )

        var bestSlot = "06h às 09h"
        var bestPeriod = "manhã"
        var bestPerHour = 58.00

        for ((label, info) in slots) {
            val (period, hourRange) = info
            val ridesInSlot = rides.filter { ride ->
                val hour = Instant.ofEpochMilli(ride.timestamp)
                    .atZone(ZoneId.systemDefault())
                    .hour
                hour in hourRange
            }

            if (ridesInSlot.isNotEmpty()) {
                val totalGross = ridesInSlot.sumOf { it.grossValue }
                val totalHours = kotlin.math.max(0.5, ridesInSlot.sumOf { it.durationMinutes } / 60.0)
                val perHour = totalGross / totalHours
                if (perHour > bestPerHour) {
                    bestPerHour = perHour
                    bestSlot = label
                    bestPeriod = period
                }
            }
        }

        return SlotResult(
            slotLabel = bestSlot,
            periodName = bestPeriod,
            formattedRatePerHour = "R$ %.2f/h".format(bestPerHour).replace('.', ',')
        )
    }
}
