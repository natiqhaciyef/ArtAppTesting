package com.natiqhaciyef.artapptesting.retrofit.api

import com.natiqhaciyef.artapptesting.retrofit.util.RetrofitDetails.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("key") apiKey : String = API_KEY,
        @Query("q") searchQue: String
    ): Response<PixabayModel>
}