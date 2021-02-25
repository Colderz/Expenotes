package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import pakiet.arkadiuszzimny.expenotes_v1.data.db.GoalRepository
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

class ArchiveViewModel @ViewModelInject constructor(
    private var goalRepository: GoalRepository
) : ViewModel() {

    private var allGoal: Deferred<LiveData<List<GoalItem>>> =
        goalRepository.getAllGoalsAsync()
    fun upsert(item: GoalItem) {
        goalRepository.upsert(item)
    }

    fun deleteGoal(item: GoalItem) {
        goalRepository.delete(item)
    }

    fun getAllGoals(): LiveData<List<GoalItem>> = runBlocking {
        allGoal.await()
    }

    fun filterArchiveGoals(list: List<GoalItem>): ArrayList<GoalItem> {
        var archiveList: ArrayList<GoalItem> = arrayListOf()
        for(item in list) {
                archiveList.add(item)

        }
        return archiveList
    }

}