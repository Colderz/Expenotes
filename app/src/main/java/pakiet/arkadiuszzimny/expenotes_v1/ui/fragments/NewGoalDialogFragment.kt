package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pakiet.arkadiuszzimny.expenotes_v1.R

class NewGoalDialogFragment: DialogFragment() {

    companion object{
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
        return inflater.inflate(R.layout.goal_layout, container, false)
    }
}