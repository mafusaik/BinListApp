package by.homework.hlazarseni.binlistapp.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.homework.hlazarseni.binlistapp.di.apiModule
import by.homework.hlazarseni.binlistapp.di.databaseModule
import by.homework.hlazarseni.binlistapp.di.repositoryModule
import by.homework.hlazarseni.binlistapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BinListApplication : Application() {

//    private var _binDatabase: BinDatabase? = null
//    val binDatabase get() = requireNotNull(_binDatabase)
//
//    override fun onCreate() {
//        super.onCreate()
//        _binDatabase = Room
//            .databaseBuilder(
//                this,
//                BinDatabase::class.java,
//                "database-room"
//            )
//            .allowMainThreadQueries()
//            .build()
//    }
//}
//
//val Context.binDatabase: BinDatabase
//    get() = when (this) {
//        is BinListApplication -> binDatabase
//        else -> applicationContext.binDatabase
//    }

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