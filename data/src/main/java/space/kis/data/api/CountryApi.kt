package space.kis.data.api

import retrofit2.http.GET
import space.kis.data.model.CountryDTO

internal interface CountryApi {
    @GET("all")
    suspend fun getCountry(): List<CountryDTO>
}