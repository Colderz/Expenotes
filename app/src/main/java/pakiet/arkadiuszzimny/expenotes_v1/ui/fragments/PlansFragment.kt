package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_plans.*
import kotlinx.android.synthetic.main.fragment_plans.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import pakiet.arkadiuszzimny.expenotes_v1.ui.PlansViewModel

@AndroidEntryPoint
class PlansFragment : Fragment() {

    private val viewModel: PlansViewModel by viewModels()
    private lateinit var listOfGoals: LiveData<List<GoalItem>>

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
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.CHANGEDEPO_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), InfoGoalDialogFragment.TAG)
            }
        })

        goalsFragmentView.editdep_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogInstance = EditDepDialogFragment.newInstance()
                dialogInstance.setTargetFragment(this@PlansFragment, viewModel.MANAGEGOAL_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), EditDepDialogFragment.TAG)
            }
        })


        return goalsFragmentView
    }

    override fun onResume() {
        super.onResume()
        listOfGoals = viewModel.getAllGoals()
        listOfGoals.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (item in it) {
                    if (item.type.equals("main")) {
                        amountGoal.text = item.goal.toString()
                        currency.visibility = View.VISIBLE
                    }
                    if(item.type.equals("fut1")) {
                        amountGoal1.text = item.goal.toString()
                        currency1.visibility = View.VISIBLE
                    }
                    if(item.type.equals("fut2")) {
                        amountGoal2.text = item.goal.toString()
                        currency2.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            viewModel.ENTERGOAL_FRAGMENT -> if(resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValue: String = bundle!!.getString("value", "error")
                var resultValue1 = bundle!!.getString("value1", "error")
                var resultValue2 = bundle!!.getString("value2", "error")
                if(!resultValue.equals("error")) {
                    amountGoal.text = resultValue
                    currency.visibility = View.VISIBLE
                    val item = GoalItem("main", Integer.valueOf(resultValue), 0)
                    viewModel.upsert(item)
                }
                if(!resultValue1.equals("error")) {
                    amountGoal1.text = resultValue1
                    currency1.visibility = View.VISIBLE
                    val item1 = GoalItem("fut1", Integer.valueOf(resultValue1), 0)
                    viewModel.upsert(item1)
                }
                if(!resultValue2.equals("error")) {
                    amountGoal2.text = resultValue2
                    currency2.visibility = View.VISIBLE
                    val item2 = GoalItem("fut2", Integer.valueOf(resultValue2), 0)
                    viewModel.upsert(item2)
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletOkFrom.setOnClickListener {
            var amount: Int = 0
            if(!amountGoal.toString().equals("NOT SET")) amount = Integer.valueOf(amountGoal.toString())
            val amountChanged: Int = amount + 50
            val itemChanged = GoalItem("main", amountChanged, Integer.valueOf(stateGoal.toString()))
            viewModel.upsert(itemChanged)
        }
    }


}