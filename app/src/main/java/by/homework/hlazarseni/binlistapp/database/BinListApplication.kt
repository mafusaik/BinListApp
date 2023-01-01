package by.homework.hlazarseni.binlistapp.database

import android.app.Application
import android.content.Context
import androidx.room.Room


class BinListApplication : Application() {

    private var _binDatabase: BinDatabase? = null
    val binDatabase get() = requireNotNull(_binDatabase)

    override fun onCreate() {
        super.onCreate()
        _binDatabase = Room
            .databaseBuilder(
                this,
                BinDatabase::class.java,
                "database-room"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.binDatabase: BinDatabase
    get() = when (this) {
        is BinListApplication -> binDatabase
        else -> applicationContext.binDatabase
    }