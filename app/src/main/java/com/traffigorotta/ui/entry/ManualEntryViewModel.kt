package com.traffigorotta.ui.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.FinancialRepository
import com.traffigorotta.data.JourneyRepository
import com.traffigorotta.model.EarningSource
import com.traffigorotta.model.EntryType
import com.traffigorotta.model.ExpenseCategory
import com.traffigorotta.model.FinancialEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ManualEntryUiState(
    val entryType: EntryType = EntryType.EARNING,
    val amountInput: String = "",
    val distanceKmInput: String = "",
    val selectedSource: EarningSource = EarningSource.UBER,
    val selectedCategory: ExpenseCategory = ExpenseCategory.COMBUSTIVEL,
    val notesInput: String = "",
    val entries: List<FinancialEntry> = emptyList(),
    val totalEarnings: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val netBalance: Double = 0.0,
    val isFormOpen: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class ManualEntryViewModel(
    private val financialRepository: FinancialRepository = FinancialRepository(),
    private val journeyRepository: JourneyRepository = JourneyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManualEntryUiState())
    val uiState: StateFlow<ManualEntryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            financialRepository.entries.collect { list ->
                val earnings = list.filter { it.type == EntryType.EARNING }.sumOf { it.amount }
                val expenses = list.filter { it.type == EntryType.EXPENSE }.sumOf { it.amount }
                _uiState.update { current ->
                    current.copy(
                        entries = list,
                        totalEarnings = earnings,
                        totalExpenses = expenses,
                        netBalance = earnings - expenses
                    )
                }
            }
        }
    }

    fun setEntryType(type: EntryType) = _uiState.update { it.copy(entryType = type, errorMessage = null) }
    fun onAmountChange(value: String) = _uiState.update { it.copy(amountInput = value, errorMessage = null) }
    fun onDistanceChange(value: String) = _uiState.update { it.copy(distanceKmInput = value) }
    fun onSourceSelect(source: EarningSource) = _uiState.update { it.copy(selectedSource = source) }
    fun onCategorySelect(category: ExpenseCategory) = _uiState.update { it.copy(selectedCategory = category) }
    fun onNotesChange(value: String) = _uiState.update { it.copy(notesInput = value) }
    fun toggleForm() = _uiState.update { it.copy(isFormOpen = !it.isFormOpen, errorMessage = null, successMessage = null) }

    fun saveEntry() {
        val state = _uiState.value
        val amount = state.amountInput.replace(',', '.').toDoubleOrNull()

        if (amount == null || amount <= 0.0) {
            _uiState.update { it.copy(errorMessage = "Digite um valor válido maior que zero.") }
            return
        }

        val km = state.distanceKmInput.replace(',', '.').toDoubleOrNull() ?: 0.0

        val newEntry = FinancialEntry(
            type = state.entryType,
            amount = amount,
            earningSource = if (state.entryType == EntryType.EARNING) state.selectedSource else null,
            expenseCategory = if (state.entryType == EntryType.EXPENSE) state.selectedCategory else null,
            distanceKm = km,
            notes = state.notesInput.trim()
        )

        financialRepository.addEntry(newEntry)

        // Se for um ganho de corrida e houver jornada ativa, adiciona à jornada
        if (state.entryType == EntryType.EARNING) {
            journeyRepository.addRideToActiveJourney(amount, km)
        }

        _uiState.update {
            it.copy(
                amountInput = "",
                distanceKmInput = "",
                notesInput = "",
                isFormOpen = false,
                errorMessage = null,
                successMessage = if (state.entryType == EntryType.EARNING) "Ganho registrado com sucesso!" else "Despesa registrada com sucesso!"
            )
        }
    }

    fun deleteEntry(id: String) {
        financialRepository.deleteEntry(id)
    }
}
