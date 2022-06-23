package space.kis.domain.usecase

import space.kis.domain.model.Country
import space.kis.domain.repository.CountryRemoteRepository

class GetCountryUseCase(private val countryRepository: CountryRemoteRepository) {

    suspend operator fun invoke(): Result<List<Country>> {
        return countryRepository.getCountryListFromApi()
    }
}