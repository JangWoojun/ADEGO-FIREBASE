package com.woojun.adego.network

import com.woojun.adego.dataClass.KakaoLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("local/search/keyword.json")
    fun getLocation(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<KakaoLocation>

}
