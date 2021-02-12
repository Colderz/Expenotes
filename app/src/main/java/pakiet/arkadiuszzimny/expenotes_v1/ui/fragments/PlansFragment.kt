package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_plans.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.main.MainViewModel
import pakiet.arkadiuszzimny.expenotes_v1.ui.PlansViewModel

@AndroidEntryPoint
class PlansFragment : Fragment() {

    private val viewModel: PlansViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val goalsFragmentView: View = inflater.inflate(R.layout.fragment_plans, container, false)
        viewModel.loadImageUsingGlide(this, goalsFragmentView.fragmentOneProgressBar, goalsFragmentView.createGoalImage)

        goalsFragmentView.createGoalImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var dialogInstance = NewGoalDialogFragment.newInstance()
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.ENTERGOAL_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), NewGoalDialogFragment.TAG)
            }
        })

        goalsFragmentView.mainGoalCard.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogInstance = InfoGoalDialogFragment.newInstance()
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.ENTERGOAL_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), InfoGoalDialogFragment.TAG)
            }
        })

        goalsFragmentView.editdep_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogInstance = EditDepDialogFragment.newInstance()
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.ENTERGOAL_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), EditDepDialogFragment.TAG)
            }
        })

        return goalsFragmentView
    }

}