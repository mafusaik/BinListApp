package by.homework.hlazarseni.binlistapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {

    @GET("/{numberCard}")
    fun getInfo(@Path("numberCard") numberCard: String ): Call<BankCardDTO>

}