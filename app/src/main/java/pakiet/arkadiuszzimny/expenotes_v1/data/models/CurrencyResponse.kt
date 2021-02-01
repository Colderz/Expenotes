package pakiet.arkadiuszzimny.expenotes_v1.data.models

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates
)