package pakiet.arkadiuszzimny.expenotes_v1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import pakiet.arkadiuszzimny.expenotes_v1.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var listOfGoals: LiveData<List<GoalItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationViewMain.setupWithNavController(navHostFragmentMain.findNavController())
    }

    override fun onResume() {
        super.onResume()
        listOfGoals = viewModel.getAllGoals()
        listOfGoals.observe(this, {
            if(it.isNotEmpty()) {
                for(item in it) {
                    if(item.type.equals("wallet")) {
                        needAmount.text = item.goal.toString()
                    }
                }
            }
        })
    }


}