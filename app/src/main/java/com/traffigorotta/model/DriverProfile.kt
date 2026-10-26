package com.traffigorotta.model

enum class FuelType(val label: String) {
    GASOLINA("Gasolina"),
    ETANOL("Etanol"),
    GNV("GNV"),
    ELETRICO("Elétrico")
}

data class VehicleData(
    val model: String = "Chevrolet Onix 1.0",
    val fuelType: FuelType = FuelType.GASOLINA,
    val fuelEfficiencyKmPerLiter: Double = 12.5,
    val fuelPricePerLiter: Double = 5.89,
    val monthlyFixedCosts: Double = 850.0,
    val monthlyWorkDays: Int = 25,
    val dailyEstimatedKm: Double = 150.0
) {
    // Custo de combustível por km: Preço por litro / Consumo médio
    val fuelCostPerKm: Double
        get() = if (fuelEfficiencyKmPerLiter > 0) fuelPricePerLiter / fuelEfficiencyKmPerLiter else 0.0

    // Custo fixo diário (seguro, manutenção, IPVA diluído pelos dias de trabalho)
    val dailyFixedCost: Double
        get() = if (monthlyWorkDays > 0) monthlyFixedCosts / monthlyWorkDays else 0.0

    // Custo total estimado por km rodado (combustível + fixo por km)
    val totalCostPerKm: Double
        get() {
            val fixedPerKm = if (dailyEstimatedKm > 0) dailyFixedCost / dailyEstimatedKm else 0.0
            return fuelCostPerKm + fixedPerKm
        }
}

data class DriverProfile(
    val name: String = "Motorista",
    val email: String = "motorista@rotta.app",
    val phone: String = "(11) 98765-4321",
    val vehicle: VehicleData = VehicleData(),
    val isLoggedIn: Boolean = false
)
