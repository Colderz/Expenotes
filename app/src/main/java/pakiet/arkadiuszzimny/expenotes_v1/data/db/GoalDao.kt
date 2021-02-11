package pakiet.arkadiuszzimny.expenotes_v1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

@Dao
interface GoalDao {

    //update or insert = upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: GoalItem)

    @Delete
    suspend fun deleteGoal(item: GoalItem)

}