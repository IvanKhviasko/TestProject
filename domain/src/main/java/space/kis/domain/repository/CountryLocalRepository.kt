package space.kis.domain.repository

import kotlinx.coroutines.flow.Flow
import space.kis.domain.model.Country

interface CountryLocalRepository {

    suspend fun getAllCountriesFromDatabase(): Result<List<Country>>

    suspend fun insertCountryToDatabase(country: Country): Result<Unit>

    suspend fun loadCountryByIdFromDatabase(id: Long): Result<Country>

    suspend fun deleteCountryFromDatabase(country: Country): Result<Unit>

    suspend fun subscribeChangesDb(): Result<Flow<List<Country>>>

}