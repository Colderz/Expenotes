package pakiet.arkadiuszzimny.expenotes_v1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

@Database(
    entities = [GoalItem::class],
    version = 1
)
abstract class GoalDatabase : RoomDatabase() {

    abstract fun getGoalDao(): GoalDao

    companion object {
        //inne wątki będą natychmiastowo widzieć zmiany
        @Volatile
        private var instance: GoalDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //wszystko co wydarzy się w tym bloku będzie dostępne w wątkach w tym samym czasie
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GoalDatabase::class.java,
                "goal_db.db"
            ).build()
    }
}