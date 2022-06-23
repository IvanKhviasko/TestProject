package space.kis.testproject.koin

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import space.kis.testproject.manager.SharedPrefersManager
import space.kis.testproject.location.LocationService
import space.kis.testproject.fragments.*
import space.kis.testproject.viewmodels.DatabaseViewModel
import space.kis.testproject.viewmodels.RetrofitViewModel

val allViewModelsModule = module {
    viewModelOf(::DatabaseViewModel)
    viewModelOf(::RetrofitViewModel)
    singleOf(::DetailsFragment)
    singleOf(::SettingsFragment)
    singleOf(::MapFragment)
    singleOf(::LocationService)
    single { SharedPrefersManager(context = get()) }
}




