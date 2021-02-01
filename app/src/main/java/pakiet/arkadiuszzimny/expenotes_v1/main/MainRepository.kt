package pakiet.arkadiuszzimny.expenotes_v1.main

import pakiet.arkadiuszzimny.expenotes_v1.data.models.CurrencyResponse
import pakiet.arkadiuszzimny.expenotes_v1.util.Resource

interface MainRepository {

    //Funkcja potrzebna dla dostępu do Api. W podstawowej formie narażamy się na błędy i ich nie obsługujemy.
    //Jeśli coś pójdzie nie tak (np. brak  internetu) -> nie będziemy mogli odczytać tego w viewModel.
    suspend fun getRates(base: String): Resource<CurrencyResponse>
}