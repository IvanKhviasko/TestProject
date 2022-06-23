package space.kis.testproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import space.kis.data.koin.allDataModule
import space.kis.testproject.koin.allViewModelsModule

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                allDataModule,
                allViewModelsModule
            )
        }
    }
}