package pakiet.arkadiuszzimny.expenotes_v1.util

import pakiet.arkadiuszzimny.expenotes_v1.data.CurrencyApi
import pakiet.arkadiuszzimny.expenotes_v1.data.models.CurrencyResponse
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

}