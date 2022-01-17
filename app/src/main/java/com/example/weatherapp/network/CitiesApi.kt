package com.example.weatherapp.network

import com.example.weatherapp.models.CitiesResponse
import retrofit2.http.*

interface CitiesApi {

    @GET("cities?")
    @Headers("x-rapidapi-host: wft-geo-db.p.rapidapi.com")
    suspend fun getCities(@Query("namePrefix") namePrefix: String,
                          @Query("sort") sort: String,
                          @Query("limit") limit: Int,
                          @Header("x-rapidapi-key") api_key: String): CitiesResponse
}