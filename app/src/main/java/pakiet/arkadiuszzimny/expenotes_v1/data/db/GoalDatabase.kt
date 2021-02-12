package pakiet.arkadiuszzimny.expenotes_v1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

@Database(
    entities = [GoalItem::class],
    version = 2
)
abstract class GoalDatabase : RoomDatabase() {

    abstract fun goalDao(): GoalDao

    companion object {
        private var instance: GoalDatabase? = null

        fun getInstance(context: Context) : GoalDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    GoalDatabase::class.java,
                    "goal_table")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase() {
            instance = null
        }
    }


}


