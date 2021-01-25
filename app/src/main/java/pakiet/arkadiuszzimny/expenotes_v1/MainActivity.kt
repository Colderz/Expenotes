package pakiet.arkadiuszzimny.expenotes_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import nl.joery.animatedbottombar.AnimatedBottomBar
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.ConverterFragment
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.DoneFragment
import pakiet.arkadiuszzimny.expenotes_v1.ui.fragments.PlansFragment as PlansFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                var fragment: Fragment? = null
                when(newTab.id) {
                    R.id.plans -> fragment = PlansFragment()
                    R.id.converter -> fragment = ConverterFragment()
                    R.id.done -> fragment = DoneFragment()
                }

                fragment?.let{
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, it).commit()
                } ?: Log.e(MainActivity::class.java.name, "Error in creating fragment")

            }
        })
    }
}