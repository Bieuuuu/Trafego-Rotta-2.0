package com.traffigorotta.data

import com.traffigorotta.model.DriverGoals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoalsRepository {

    private val _goals = MutableStateFlow(
        DriverGoals(
            dailyGoalAmount = 500.0,
            weeklyGoalAmount = 2500.0,
            monthlyGoalAmount = 10000.0,
            daysOffPerWeek = 1
        )
    )

    val goals: StateFlow<DriverGoals> = _goals.asStateFlow()

    fun updateGoals(daily: Double, weekly: Double, monthly: Double, daysOff: Int = 1) {
        _goals.update {
            it.copy(
                dailyGoalAmount = daily,
                weeklyGoalAmount = weekly,
                monthlyGoalAmount = monthly,
                daysOffPerWeek = daysOff
            )
        }
    }
}
