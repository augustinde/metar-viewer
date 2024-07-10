package fr.serkox.metarviewer.network

import fr.serkox.metarviewer.BuildConfig
import fr.serkox.metarviewer.data.model.dto.ResponseStationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StationApiService {
    private val apiKey: String
        get() = BuildConfig.WEATHER_API_KEY
    @GET("station/{icao}")
    suspend fun getStationInfo(@Path("icao") icao: String, @Query("x-api-key") apiKey: String = this.apiKey): ResponseStationDto
}