package com.traffigorotta.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.ReportsRepository
import com.traffigorotta.model.PeriodSummary
import com.traffigorotta.model.ReportPeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReportsUiState(
    val selectedPeriod: ReportPeriod = ReportPeriod.TODAY,
    val summary: PeriodSummary? = null
)

class ReportsViewModel(
    private val reportsRepository: ReportsRepository = ReportsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadPeriod(ReportPeriod.TODAY)
    }

    fun selectPeriod(period: ReportPeriod) {
        _uiState.update { it.copy(selectedPeriod = period) }
        loadPeriod(period)
    }

    private fun loadPeriod(period: ReportPeriod) {
        viewModelScope.launch {
            reportsRepository.getSummary(period).collect { summary ->
                _uiState.update { it.copy(summary = summary) }
            }
        }
    }
}
