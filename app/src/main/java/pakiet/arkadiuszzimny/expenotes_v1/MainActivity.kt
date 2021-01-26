package pakiet.arkadiuszzimny.expenotes_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import nl.joery.animatedbottombar.AnimatedBottomBar
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.ConverterFragment
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.DoneFragment
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.PlansFragment as PlansFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(NavHostFragment.findNavController())
    }
}