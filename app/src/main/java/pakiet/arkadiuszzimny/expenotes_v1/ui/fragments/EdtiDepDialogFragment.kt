package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.editdep_layout.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class EditDepDialogFragment: DialogFragment() {

    companion object{
        const val TAG = "EditDepDialogFragment"
        private lateinit var fragment: EditDepDialogFragment

        fun newInstance(): EditDepDialogFragment {
            fragment = EditDepDialogFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newGoalDialogView = inflater.inflate(R.layout.editdep_layout, container, false)
        return newGoalDialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener(view)
    }

    private fun setupClickListener(view: View) {
        view.savebtn.setOnClickListener {
            dismiss()
        }
    }


}