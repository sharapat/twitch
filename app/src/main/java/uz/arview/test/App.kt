package uz.arview.test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.arview.test.di.localDataModule
import uz.arview.test.di.networkModule
import uz.arview.test.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(localDataModule, networkModule, viewModelModule)
        startKoin { // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            koin.loadModules(modules)
        }
    }
}