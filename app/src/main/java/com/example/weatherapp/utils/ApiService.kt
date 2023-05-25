package com.example.weatherapp.utils

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//interface ApiService {
//    @GET("Lagos")
//    suspend fun getWeatherData(): Response<WeatherResponse>
//}
interface ApiService {
    @GET("current.json")
    suspend fun getWeatherData(
        @Query("key") apiKey: String = "a225bffd7dad4e8cbd2144636230504",
        @Query("q") location: String
    ): Response<WeatherResponse>
}