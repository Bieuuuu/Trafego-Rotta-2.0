package com.traffigorotta.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffigorotta.data.FinancialRepository
import com.traffigorotta.data.GoalsRepository
import com.traffigorotta.data.JourneyRepository
import com.traffigorotta.model.DriverGoals
import com.traffigorotta.model.EntryType
import com.traffigorotta.model.GoalProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GoalsUiState(
    val dailyProgress: GoalProgress = GoalProgress(500.0, 485.0, 97.0, 15.0, false),
    val weeklyProgress: GoalProgress = GoalProgress(2500.0, 1840.0, 73.6, 660.0, false),
    val monthlyProgress: GoalProgress = GoalProgress(10000.0, 6920.0, 69.2, 3080.0, false),
    val isEditing: Boolean = false,
    val dailyInput: String = "500",
    val weeklyInput: String = "2500",
    val monthlyInput: String = "10000",
    val savedMessage: String? = null
)

class GoalsViewModel(
    private val goalsRepository: GoalsRepository = GoalsRepository(),
    private val financialRepository: FinancialRepository = FinancialRepository(),
    private val journeyRepository: JourneyRepository = JourneyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalsUiState())
    val uiState: StateFlow<GoalsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            goalsRepository.goals.collect { goals ->
                _uiState.update { current ->
                    current.copy(
                        dailyInput = "%.0f".format(goals.dailyGoalAmount),
                        weeklyInput = "%.0f".format(goals.weeklyGoalAmount),
                        monthlyInput = "%.0f".format(goals.monthlyGoalAmount),
                        dailyProgress = goals.calculateDailyProgress(485.20),
                        weeklyProgress = goals.calculateWeeklyProgress(1840.00),
                        monthlyProgress = goals.calculateMonthlyProgress(6920.00)
                    )
                }
            }
        }

        viewModelScope.launch {
            financialRepository.entries.collect { entries ->
                val dayEarnings = entries.filter { it.type == EntryType.EARNING }.sumOf { it.amount }
                val currentDaily = if (dayEarnings > 0) dayEarnings + 380.0 else 485.20
                val goals = goalsRepository.goals.value

                _uiState.update { current ->
                    current.copy(
                        dailyProgress = goals.calculateDailyProgress(currentDaily)
                    )
                }
            }
        }
    }

    fun onDailyInputChange(value: String) = _uiState.update { it.copy(dailyInput = value, savedMessage = null) }
    fun onWeeklyInputChange(value: String) = _uiState.update { it.copy(weeklyInput = value, savedMessage = null) }
    fun onMonthlyInputChange(value: String) = _uiState.update { it.copy(monthlyInput = value, savedMessage = null) }
    fun toggleEdit() = _uiState.update { it.copy(isEditing = !it.isEditing, savedMessage = null) }

    fun saveGoals() {
        val daily = _uiState.value.dailyInput.replace(',', '.').toDoubleOrNull() ?: 500.0
        val weekly = _uiState.value.weeklyInput.replace(',', '.').toDoubleOrNull() ?: 2500.0
        val monthly = _uiState.value.monthlyInput.replace(',', '.').toDoubleOrNull() ?: 10000.0

        goalsRepository.updateGoals(daily, weekly, monthly)
        _uiState.update {
            it.copy(
                isEditing = false,
                savedMessage = "Metas atualizadas com sucesso!"
            )
        }
    }
}
