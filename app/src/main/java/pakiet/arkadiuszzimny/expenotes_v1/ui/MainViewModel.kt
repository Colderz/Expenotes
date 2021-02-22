package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pakiet.arkadiuszzimny.expenotes_v1.data.db.GoalRepository

class MainViewModel @ViewModelInject constructor(application: Application, private var goalRepository: GoalRepository) : ViewModel() {

    


}