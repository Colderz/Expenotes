package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_conv.*
import kotlinx.android.synthetic.main.fragment_conv.view.*
import kotlinx.android.synthetic.main.fragment_plans.*
import kotlinx.coroutines.flow.collect
import pakiet.arkadiuszzimny.expenotes_v1.ui.MainActivity
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.ui.ConverterViewModel

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is ConverterViewModel.CurrencyEvent.Success -> {
                        tvAmountRight.text = event.resultText
                        tvGoalSecond.text = event.resultGoalText
                    }
                    is ConverterViewModel.CurrencyEvent.Failure -> {
                        tvAmountRight.text = event.errorText
                    }
                    is ConverterViewModel.CurrencyEvent.Loading -> {

                    }
                    else -> Unit
                }
            }
        }
        val converterFragmentView = inflater.inflate(R.layout.fragment_conv, container, false)
        converterFragmentView.tvAmountLeft.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var dialogInstance = AmountDialogFragment.newInstance("Amount", "Editing")
                dialogInstance.setTargetFragment(
                    this@ConverterFragment,
                    viewModel.ENTERAMOUNT_FRAGMENT
                )
                dialogInstance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
                dialogInstance.show(
                    parentFragmentManager.beginTransaction(),
                    AmountDialogFragment.TAG
                )
            }
        })
        converterFragmentView.btnConvert.setOnClickListener {
            viewModel.convert(
                tvAmountLeft.text.toString(),
                tvFromCurrency.text.toString(),
                tvToCurrency.text.toString(),
                tvGoalFirst.text.toString(),
                goalCurrency2Goal.text.toString()
            )
        }
        converterFragmentView.pickerFrom.minValue = 0
        converterFragmentView.pickerFrom.maxValue = 31
        converterFragmentView.pickerTo.minValue = 0
        converterFragmentView.pickerTo.maxValue = 31
        converterFragmentView.pickerFrom.displayedValues = viewModel.arrayOfCurrency
        converterFragmentView.pickerTo.displayedValues = viewModel.arrayOfCurrency
        converterFragmentView.pickerFrom.value = 20
        converterFragmentView.pickerTo.value = 1


        converterFragmentView.pickerFrom.setOnScrollListener({ picker, state ->
            if (state == SCROLL_STATE_IDLE) {
                picker.postDelayed({
                    tvFromCurrency.text = viewModel.arrayOfCurrency.get(picker.value)
                }, 200)
            }
        })
        converterFragmentView.pickerTo.setOnScrollListener({ picker, state ->
            if (state == SCROLL_STATE_IDLE) {
                picker.postDelayed({
                    tvToCurrency.text = viewModel.arrayOfCurrency.get(picker.value)
                    goalCurrency2Goal.text = viewModel.arrayOfCurrency.get(picker.value)
                }, 200)
            }
        })
        converterFragmentView.imageReplace.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val temporaryValue: String = tvFromCurrency.text.toString()
                tvFromCurrency.text = tvToCurrency.text.toString()
                tvToCurrency.text = temporaryValue
                val temporaryPickerValue = pickerFrom.value
                pickerFrom.value = pickerTo.value
                pickerTo.value = temporaryPickerValue
                viewModel.convert(
                    tvAmountLeft.text.toString(),
                    tvFromCurrency.text.toString(),
                    tvToCurrency.text.toString(),
                    tvGoalFirst.text.toString(),
                    goalCurrency2Goal.text.toString()
                )
            }
        })
        var activ: MainActivity = activity as MainActivity
        converterFragmentView.tvGoalFirst.text = activ.needAmount.text.toString()
        return converterFragmentView
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            viewModel.ENTERAMOUNT_FRAGMENT -> if (resultCode == Activity.RESULT_OK) {
                var bundle = data!!.extras
                var resultValue = bundle!!.getString("value", "error")
                if(resultValue.equals("")) {
                    tvAmountLeft.text = "0"
                } else {
                    tvAmountLeft.text = resultValue
                }
            }
        }

    }


}