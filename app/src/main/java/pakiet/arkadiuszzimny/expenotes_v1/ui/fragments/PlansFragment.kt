package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_plans.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.main.MainViewModel

@AndroidEntryPoint
class PlansFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val goalsFragmentView: View = inflater.inflate(R.layout.fragment_plans, container, false)
        viewModel.loadImageUsingGlide(this, goalsFragmentView.fragmentOneProgressBar, goalsFragmentView.createGoalImage)

        return goalsFragmentView
    }

}