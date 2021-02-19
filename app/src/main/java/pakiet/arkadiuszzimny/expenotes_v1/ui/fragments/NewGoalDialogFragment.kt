package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.goal_layout.*
import kotlinx.android.synthetic.main.goal_layout.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem
import pakiet.arkadiuszzimny.expenotes_v1.ui.PlansViewModel

class NewGoalDialogFragment : DialogFragment() {


    companion object {
        const val TAG = "NewGoalDialog"
        private lateinit var fragment: NewGoalDialogFragment

        fun newInstance(): NewGoalDialogFragment {
            fragment = NewGoalDialogFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newGoalDialogView = inflater.inflate(R.layout.goal_layout, container, false)
        return newGoalDialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener(view)
    }

    private fun setupClickListener(view: View) {
        view.savebtn.setOnClickListener {
            var intent = Intent()
            intent.putExtra("value", etGoal.text.toString())
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
        view.savebtn1.setOnClickListener {
            var intent1 = Intent()
            intent1.putExtra("value1", etGoal.text.toString())
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent1)
            dismiss()
        }
        view.savebtn2.setOnClickListener {
            var intent2 = Intent()
            intent2.putExtra("value2", etGoal.text.toString())
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent2)
            dismiss()
        }
    }
}