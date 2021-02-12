package pakiet.arkadiuszzimny.expenotes_v1.data.db

import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

class GoalRepository(
    private val db: GoalDatabase
) {

    suspend fun upsert(item: GoalItem) = db.getGoalDao().upsert(item)

    suspend fun delete(item: GoalItem) = db.getGoalDao().deleteGoal(item)

    fun getAllGoals() = db.getGoalDao().getAllGaols()
}