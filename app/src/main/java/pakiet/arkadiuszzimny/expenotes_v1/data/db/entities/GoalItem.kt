package pakiet.arkadiuszzimny.expenotes_v1.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_table")
data class GoalItem(
   @ColumnInfo(name="type")
   var type: String,
   @ColumnInfo(name="goal")
   var goal: Int,
   @ColumnInfo(name="state")
   var state: Int
) {
   @PrimaryKey(autoGenerate = true)
   var goal_id: Int = 0
}