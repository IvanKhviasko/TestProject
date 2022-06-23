package space.kis.domain.repository

import space.kis.domain.model.Country

interface CountryRemoteRepository {

    suspend fun getCountryListFromApi(): Result<List<Country>>

}