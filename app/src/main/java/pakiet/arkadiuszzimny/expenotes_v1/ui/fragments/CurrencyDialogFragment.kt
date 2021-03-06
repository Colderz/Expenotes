package pakiet.arkadiuszzimny.expenotes_v1.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_currency_layout.*
import kotlinx.android.synthetic.main.dialog_currency_layout.view.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class CurrencyDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "CurrencyDialog"
        private lateinit var fragment: CurrencyDialogFragment
        lateinit var arrayCurrency: Array<String>

        fun newInstance(arrayData: Array<String>): CurrencyDialogFragment {
            fragment = CurrencyDialogFragment()
            arrayCurrency = arrayData
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainCurrencyView = inflater.inflate(R.layout.dialog_currency_layout, container, false)
        return mainCurrencyView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener(view)
    }

    private fun setupClickListener(view: View) {
        var currency = ""
        view.savebtn.setOnClickListener {
            for (item in arrayCurrency) {
                if (etCurrency.text.toString().toUpperCase().equals(item)) {
                    Toast.makeText(context, "Valid currency", Toast.LENGTH_SHORT).show()
                    currency = item
                }
            }
            var intent = Intent()
            if (!currency.equals("")) {
                intent.putExtra("currency", currency)
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                dismiss()
            } else {
                Toast.makeText(context, R.string.validCurr, Toast.LENGTH_SHORT).show()
                etCurrency.setText("")
            }
        }
    }

}