package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.goal_layout_info.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class InfoGoalDialogFragment: DialogFragment() {

    companion object{
        const val TAG = "InfoGoalDialog"
        private lateinit var fragment: InfoGoalDialogFragment

        fun newInstance(): InfoGoalDialogFragment {
            fragment = InfoGoalDialogFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infoGoalDialogView = inflater.inflate(R.layout.goal_layout_info, container, false)
        return infoGoalDialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener(view)
    }

    private fun setupClickListener(view: View) {
        view.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}