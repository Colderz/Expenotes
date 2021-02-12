package pakiet.arkadiuszzimny.expenotes_v1.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import javax.inject.Inject

class GoalRepository @Inject constructor(application: Application) {

    private var goalDao: GoalDao

    init {
        val database = GoalDatabase
            .getInstance(application.applicationContext)

        goalDao = database!!.goalDao()
    }

    fun upsert(item: GoalItem) =
        CoroutineScope(Dispatchers.IO).launch {
            goalDao.upsert(item)
        }

    fun delete(item: GoalItem) =
        CoroutineScope(Dispatchers.IO).launch {
            goalDao.deleteGoal(item)
        }

    fun getAllGoalsAsync() : Deferred<LiveData<List<GoalItem>>> =
        CoroutineScope(Dispatchers.IO).async {
            goalDao.getAllGaols()
        }

}