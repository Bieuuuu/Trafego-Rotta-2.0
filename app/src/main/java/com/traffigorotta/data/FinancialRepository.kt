package com.traffigorotta.data

import com.traffigorotta.model.EarningSource
import com.traffigorotta.model.EntryType
import com.traffigorotta.model.ExpenseCategory
import com.traffigorotta.model.FinancialEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class FinancialRepository {

    private val _entries = MutableStateFlow<List<FinancialEntry>>(
        listOf(
            FinancialEntry(
                id = "1",
                type = EntryType.EARNING,
                amount = 28.50,
                timestamp = LocalDateTime.now().minusHours(2).minusMinutes(40),
                earningSource = EarningSource.UBER,
                distanceKm = 8.4,
                notes = "Centro para Vila Mariana"
            ),
            FinancialEntry(
                id = "2",
                type = EntryType.EARNING,
                amount = 34.00,
                timestamp = LocalDateTime.now().minusHours(2).minusMinutes(10),
                earningSource = EarningSource.APP_99,
                distanceKm = 11.2,
                notes = "Aeroporto para Moema"
            ),
            FinancialEntry(
                id = "3",
                type = EntryType.EXPENSE,
                amount = 80.00,
                timestamp = LocalDateTime.now().minusHours(1).minusMinutes(45),
                expenseCategory = ExpenseCategory.COMBUSTIVEL,
                notes = "Gasolina Posto Shell"
            ),
            FinancialEntry(
                id = "4",
                type = EntryType.EARNING,
                amount = 42.80,
                timestamp = LocalDateTime.now().minusHours(1).minusMinutes(15),
                earningSource = EarningSource.UBER,
                distanceKm = 14.0,
                notes = "Pinheiros para Itaim"
            ),
            FinancialEntry(
                id = "5",
                type = EntryType.EXPENSE,
                amount = 22.00,
                timestamp = LocalDateTime.now().minusMinutes(35),
                expenseCategory = ExpenseCategory.ALIMENTACAO,
                notes = "Almoço PF"
            )
        )
    )

    val entries: StateFlow<List<FinancialEntry>> = _entries.asStateFlow()

    fun addEntry(entry: FinancialEntry) {
        _entries.update { listOf(entry) + it }
    }

    fun deleteEntry(id: String) {
        _entries.update { current -> current.filterNot { it.id == id } }
    }
}
