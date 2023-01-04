package by.homework.hlazarseni.binlistapp.database

import android.app.Application
import by.homework.hlazarseni.binlistapp.di.apiModule
import by.homework.hlazarseni.binlistapp.di.databaseModule
import by.homework.hlazarseni.binlistapp.di.repositoryModule
import by.homework.hlazarseni.binlistapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BinListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BinListApplication)
            modules(
                apiModule,
                databaseModule,
                viewModelModule,
                repositoryModule
            )
        }
    }
}