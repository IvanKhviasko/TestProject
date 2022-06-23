package space.kis.data.koin

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import space.kis.data.repository.LocalRepositoryImpl
import space.kis.data.repository.RemoteRepositoryImpl
import space.kis.domain.repository.CountryLocalRepository
import space.kis.domain.repository.CountryRemoteRepository

internal val repositoryModule = module {

    singleOf(::LocalRepositoryImpl) {
        bind<CountryLocalRepository>()
    }

    singleOf(::RemoteRepositoryImpl) {
        bind<CountryRemoteRepository>()
    }
}