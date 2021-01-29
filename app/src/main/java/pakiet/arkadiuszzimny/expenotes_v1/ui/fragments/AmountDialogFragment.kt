package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class AmountDialogFragment: DialogFragment() {

    companion object {
        const val TAG = "AmountDialog"
        private const val KEY_TITLE ="KET_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"
        private lateinit var fragment: AmountDialogFragment

        fun newInstance(title: String, subTitle: String): AmountDialogFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            fragment = AmountDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListener(view)

    }

    private fun setupView(view: View) {
        view.dialogName.text = arguments?.getString(KEY_TITLE)
        view.subTitle.text = arguments?.getString(KEY_SUBTITLE)

    }

    private fun setupClickListener(view: View) {
        view.savebtn.setOnClickListener {
            dismiss()
        }
    }
}