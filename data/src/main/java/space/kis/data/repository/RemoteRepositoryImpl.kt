package space.kis.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.kis.data.mapper.toDomainModelsCountryRemote
import space.kis.data.api.CountryApi
import space.kis.domain.model.Country
import space.kis.domain.repository.CountryRemoteRepository

internal class RemoteRepositoryImpl(private val countryApi: CountryApi) :
    CountryRemoteRepository {

    override suspend fun getCountryListFromApi(): Result<List<Country>> {
        return withContext(Dispatchers.IO) {
            runCatching { countryApi.getCountry().toDomainModelsCountryRemote() }
        }
    }
}