package com.covid.covidapps.datasource

import retrofit2.http.POST
import retrofit2.http.Query

interface Service {
    @POST("predict")
    suspend fun classify(
        @Query("x1") x1 : String,
        @Query("x2") x2 : String,
        @Query("x3") x3 : String,
        @Query("x4") x4 : String,
        @Query("x5") x5 : String
    ): String

}