package space.kis.data.koin

import space.kis.domain.usecase.DeleteCountryFromDatabaseUseCase
import space.kis.domain.usecase.GetCountryUseCase
import space.kis.domain.usecase.GetFavoriteInsertUseCase
import space.kis.domain.usecase.GetSubscribeToDatabaseUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val getCountryUseCase = module {
    factoryOf(::GetCountryUseCase)
}

val deleteCountryFromDatabaseUseCase = module {
    factoryOf(::DeleteCountryFromDatabaseUseCase)
}

val getFavoriteInsertUseCase = module {
    factoryOf(::GetFavoriteInsertUseCase)
}

val getSubscribeToDatabaseUseCase = module {
    factoryOf(::GetSubscribeToDatabaseUseCase)
}