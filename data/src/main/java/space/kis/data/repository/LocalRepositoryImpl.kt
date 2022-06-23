package space.kis.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import space.kis.data.mapper.toDataModelEntity
import space.kis.data.mapper.toDomainModel
import space.kis.data.mapper.toDomainModelsCountryLocal
import space.kis.data.room.CountryDao
import space.kis.domain.model.Country
import space.kis.domain.repository.CountryLocalRepository

internal class LocalRepositoryImpl(private val dao: CountryDao) : CountryLocalRepository {

    override suspend fun getAllCountriesFromDatabase(): Result<List<Country>> {
        return withContext(Dispatchers.IO) {
            runCatching { dao.getAll().toDomainModelsCountryLocal() }
        }
    }

    override suspend fun insertCountryToDatabase(country: Country): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching { dao.insertCountry(country.toDataModelEntity()) }
        }
    }

    override suspend fun loadCountryByIdFromDatabase(id: Long): Result<Country> {
        return withContext(Dispatchers.IO) {
            runCatching { dao.loadCountryById(id).toDomainModel() }
        }
    }

    override suspend fun deleteCountryFromDatabase(country: Country): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching { dao.delete(country.toDataModelEntity()) }
        }
    }

    override suspend fun subscribeChangesDb(): Result<Flow<List<Country>>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dao.subscribeChanges().map {
                    it.toDomainModelsCountryLocal()
                }
            }
        }
    }
}