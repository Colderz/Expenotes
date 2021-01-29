package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_conv.view.*
import kotlinx.android.synthetic.main.fragment_converter.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class ConverterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inputFragmentView = inflater.inflate(R.layout.fragment_conv, container, false)
        inputFragmentView.tvAmountLeft.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var dialogInstance = AmountDialogFragment.newInstance("Amount", "Editing")
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager, AmountDialogFragment.TAG)
            }
        })
        return inputFragmentView
    }


}