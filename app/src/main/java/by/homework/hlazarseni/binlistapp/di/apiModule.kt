package by.homework.hlazarseni.binlistapp.di

import by.homework.hlazarseni.binlistapp.api.BinApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://lookup.binlist.net"

internal val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create<BinApi>() }
}