package space.kis.data.koin

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import space.kis.data.api.CountryApi

internal val networkModule = module {
    single {
        OkHttpClient
            .Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://countriesinfo21.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create<CountryApi>()
    }
}