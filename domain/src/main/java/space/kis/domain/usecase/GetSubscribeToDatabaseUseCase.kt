package space.kis.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.kis.domain.model.Country
import space.kis.domain.repository.CountryLocalRepository

class GetSubscribeToDatabaseUseCase(private val countryRepository: CountryLocalRepository) {
    suspend operator fun invoke(): Result<Flow<List<Country>>> {
        return countryRepository.subscribeChangesDb()
    }
}