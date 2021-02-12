package pakiet.arkadiuszzimny.expenotes_v1.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

@Dao
interface GoalDao {

    //update or insert = upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: GoalItem)

    @Delete
    suspend fun deleteGoal(item: GoalItem)

    @Query("SELECT * FROM goals")
    fun getAllGaols() : LiveData<List<GoalItem>>

}