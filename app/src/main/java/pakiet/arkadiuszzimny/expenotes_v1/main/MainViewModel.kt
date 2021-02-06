package pakiet.arkadiuszzimny.expenotes_v1.main

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pakiet.arkadiuszzimny.expenotes_v1.data.models.Rates
import pakiet.arkadiuszzimny.expenotes_v1.util.DispatcherProvider
import pakiet.arkadiuszzimny.expenotes_v1.util.Resource
import java.lang.Math.round
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    val ENTERAMOUNT_FRAGMENT = 1
    val ENTERGOAL_FRAGMENT = 1
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion
    val arrayOfCurrency = arrayOf("CAD", "EUR", "HKD", "ISK", "PHP", "DKK", "HUF", "CZK", "AUD", "RON", "SEK", "IDR",
        "INR", "BRL", "RUB", "HRK", "JPY", "THB", "CHF", "SGD", "PLN", "BGN", "CNY", "NOK", "NZD", "ZAR", "USD", "MXN", "ILS", "GBP", "KRW", "MYR")
    private val imageUrl = "https://images.unsplash.com/photo-1507925921958-8a62f3d1a50d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1355&q=80"

    fun loadImageUsingGlide(
        fragment: Fragment,
        progressBar: ProgressBar,
        imageView: ImageView
    )
    {
        progressBar.visibility = View.VISIBLE
        Glide.with(fragment).load(imageUrl).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.VISIBLE
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.INVISIBLE
                return false
            }
        }).into(imageView)
    }

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Nieprawidłowa wartość")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRates(fromCurrency)) {
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    if(rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Błąd")
                    } else {
                        val convertedCurrency = fromAmount * rate
                        val decimal = BigDecimal(convertedCurrency).setScale(2, RoundingMode.HALF_EVEN)
                        _conversion.value = CurrencyEvent.Success(
                            "$decimal"
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