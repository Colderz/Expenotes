package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.app.Activity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_plans.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pakiet.arkadiuszzimny.expenotes_v1.data.models.Rates
import pakiet.arkadiuszzimny.expenotes_v1.util.MainRepository
import pakiet.arkadiuszzimny.expenotes_v1.util.DispatcherProvider
import pakiet.arkadiuszzimny.expenotes_v1.util.Resource
import java.math.BigDecimal
import java.math.RoundingMode

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String, val resultGoalText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    val ENTERAMOUNT_FRAGMENT = 1
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion
    val arrayOfCurrency = arrayOf(
        "CAD",
        "EUR",
        "HKD",
        "ISK",
        "PHP",
        "DKK",
        "HUF",
        "CZK",
        "AUD",
        "RON",
        "SEK",
        "IDR",
        "INR",
        "BRL",
        "RUB",
        "HRK",
        "JPY",
        "THB",
        "CHF",
        "SGD",
        "PLN",
        "BGN",
        "CNY",
        "NOK",
        "NZD",
        "ZAR",
        "USD",
        "MXN",
        "ILS",
        "GBP",
        "KRW",
        "MYR"
    )


    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String,
        amountGoal: String,
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        val fromAmountGoal = amountGoal.toFloatOrNull()
        if (fromAmount == null || fromAmountGoal == null) {
            _conversion.value = CurrencyEvent.Failure("Nieprawidłowa wartość")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRates(fromCurrency)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    if (rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Błąd")
                    } else {
                        val convertedCurrency = fromAmount * rate
                        val convertedGoal1 = fromAmountGoal * rate
                        val decimal =
                            BigDecimal(convertedCurrency).setScale(2, RoundingMode.HALF_EVEN)
                        val decimal2 =
                            BigDecimal(convertedGoal1).setScale(2, RoundingMode.HALF_EVEN)
                        _conversion.value = CurrencyEvent.Success(
                            "$decimal", "$decimal2"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        else -> null
    }


}