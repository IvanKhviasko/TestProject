package space.kis.domain.usecase

import space.kis.domain.model.Country
import space.kis.domain.repository.CountryLocalRepository

class DeleteCountryFromDatabaseUseCase(private val countryRepository: CountryLocalRepository) {

    suspend operator fun invoke(country: Country): Result<Unit> {
        return countryRepository.deleteCountryFromDatabase(country)
    }
}