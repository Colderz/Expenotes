package pakiet.arkadiuszzimny.expenotes_v1.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalItem(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="type")
    var type: String,
    @ColumnInfo(name="goal")
    var goal: Int,
    @ColumnInfo(name="state")
    var state: Int
)