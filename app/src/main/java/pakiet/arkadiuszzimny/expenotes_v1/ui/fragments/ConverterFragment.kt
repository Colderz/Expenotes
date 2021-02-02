package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_conv.*
import kotlinx.android.synthetic.main.fragment_conv.view.*
import kotlinx.coroutines.flow.collect
import pakiet.arkadiuszzimny.expenotes_v1.MainActivity
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.main.MainViewModel

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    val ENTERAMOUNT_FRAGMENT = 1
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        tvAmountRight.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        tvAmountRight.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {

                    }
                    else -> Unit
                }
            }
        }
        val inputFragmentView = inflater.inflate(R.layout.fragment_conv, container, false)
        inputFragmentView.tvAmountLeft.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var dialogInstance = AmountDialogFragment.newInstance("Amount", "Editing")
                dialogInstance.setTargetFragment(this@ConverterFragment, ENTERAMOUNT_FRAGMENT)
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(parentFragmentManager.beginTransaction(), AmountDialogFragment.TAG)
            }
        })
        inputFragmentView.btnConvert.setOnClickListener {
            viewModel.convert(
                tvAmountLeft.text.toString(),
                tvFromCurrency.text.toString(),
                tvToCurrency.text.toString()
            )
        }
        return inputFragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ENTERAMOUNT_FRAGMENT -> if(resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValue = bundle!!.getString("value","error")
                tvAmountLeft.text = resultValue
            }
        }
    }




}