package space.kis.data.koin

import org.koin.dsl.module

val allDataModule = module {
    includes(
        databaseModule,
        networkModule,
        repositoryModule,
        getCountryUseCase,
        deleteCountryFromDatabaseUseCase,
        getFavoriteInsertUseCase,
        getSubscribeToDatabaseUseCase
    )
}