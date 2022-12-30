package by.homework.hlazarseni.binlistapp.di

import androidx.room.Room
import by.homework.hlazarseni.binlistapp.database.BinDatabase

import org.koin.dsl.module

internal val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            BinDatabase::class.java,
            "node-database"
        )
           // .allowMainThreadQueries()
            .build()
    }
    single { get<BinDatabase>().binDao }
}