package space.kis.data.koin

import androidx.room.Room
import org.koin.dsl.module
import space.kis.data.room.CountryDatabase

internal val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CountryDatabase::class.java,
            "countryDatabase_db"
        )
            .build()
    }

    single {
        get<CountryDatabase>().countryDao()
    }
}