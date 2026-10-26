package com.traffigorotta.model

data class DriverGoals(
    val dailyGoalAmount: Double = 500.0,
    val weeklyGoalAmount: Double = 2500.0,
    val monthlyGoalAmount: Double = 10000.0,
    val daysOffPerWeek: Int = 1
) {
    fun calculateDailyProgress(currentDailyEarnings: Double): GoalProgress {
        val percentage = if (dailyGoalAmount > 0) (currentDailyEarnings / dailyGoalAmount) * 100 else 0.0
        val remaining = (dailyGoalAmount - currentDailyEarnings).coerceAtLeast(0.0)
        return GoalProgress(
            target = dailyGoalAmount,
            current = currentDailyEarnings,
            percentage = percentage,
            remaining = remaining,
            isCompleted = currentDailyEarnings >= dailyGoalAmount
        )
    }

    fun calculateWeeklyProgress(currentWeeklyEarnings: Double): GoalProgress {
        val percentage = if (weeklyGoalAmount > 0) (currentWeeklyEarnings / weeklyGoalAmount) * 100 else 0.0
        val remaining = (weeklyGoalAmount - currentWeeklyEarnings).coerceAtLeast(0.0)
        return GoalProgress(
            target = weeklyGoalAmount,
            current = currentWeeklyEarnings,
            percentage = percentage,
            remaining = remaining,
            isCompleted = currentWeeklyEarnings >= weeklyGoalAmount
        )
    }

    fun calculateMonthlyProgress(currentMonthlyEarnings: Double): GoalProgress {
        val percentage = if (monthlyGoalAmount > 0) (currentMonthlyEarnings / monthlyGoalAmount) * 100 else 0.0
        val remaining = (monthlyGoalAmount - currentMonthlyEarnings).coerceAtLeast(0.0)
        return GoalProgress(
            target = monthlyGoalAmount,
            current = currentMonthlyEarnings,
            percentage = percentage,
            remaining = remaining,
            isCompleted = currentMonthlyEarnings >= monthlyGoalAmount
        )
    }
}

data class GoalProgress(
    val target: Double,
    val current: Double,
    val percentage: Double,
    val remaining: Double,
    val isCompleted: Boolean
) {
    val progressFraction: Float
        get() = (percentage / 100.0).coerceIn(0.0, 1.0).toFloat()
}
