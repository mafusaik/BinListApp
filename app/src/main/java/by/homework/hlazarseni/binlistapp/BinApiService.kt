package by.homework.hlazarseni.binlistapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object BinApiService {

    private const val BASE_URL = "https://lookup.binlist.net"

    val binApi by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<BinApi>()
    }
}