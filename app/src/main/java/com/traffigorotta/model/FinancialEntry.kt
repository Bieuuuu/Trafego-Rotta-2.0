package com.traffigorotta.model

import java.time.LocalDateTime

enum class EntryType(val label: String) {
    EARNING("Ganho"),
    EXPENSE("Despesa")
}

enum class EarningSource(val label: String) {
    UBER("Uber"),
    APP_99("99"),
    PARTICULAR("Particular"),
    OUTROS("Outros")
}

enum class ExpenseCategory(val label: String) {
    COMBUSTIVEL("Combustível"),
    ALIMENTACAO("Alimentação"),
    PEDAGIO("Pedágio"),
    LAVAGEM("Lavagem"),
    MANUTENCAO("Manutenção"),
    OUTROS("Outros")
}

data class FinancialEntry(
    val id: String = "entry_${System.currentTimeMillis()}_${(100..999).random()}",
    val type: EntryType = EntryType.EARNING,
    val amount: Double = 0.0,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val earningSource: EarningSource? = EarningSource.UBER,
    val expenseCategory: ExpenseCategory? = null,
    val distanceKm: Double = 0.0,
    val notes: String = ""
)
