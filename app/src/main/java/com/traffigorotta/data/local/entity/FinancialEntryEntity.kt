package com.traffigorotta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para persistência local de lançamentos financeiros (Ganhos e Despesas).
 * 100% no aparelho (LGPD).
 */
@Entity(tableName = "financial_entries")
data class FinancialEntryEntity(
    @PrimaryKey
    val id: String,
    val type: String, // "EARNING" ou "EXPENSE"
    val amount: Double,
    val timestampIso: String,
    val earningSource: String? = null, // "UBER", "APP_99", "PARTICULAR", "OUTROS"
    val expenseCategory: String? = null, // "COMBUSTIVEL", "ALIMENTACAO", "PEDAGIO", "LAVAGEM", "MANUTENCAO"
    val distanceKm: Double? = null,
    val notes: String = ""
)
