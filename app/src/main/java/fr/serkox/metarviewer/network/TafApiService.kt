package fr.serkox.metarviewer.network

import fr.serkox.metarviewer.BuildConfig
import fr.serkox.metarviewer.data.model.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TafApiService {

    private val apiKey: String
        get() = BuildConfig.WEATHER_API_KEY
    @GET("taf/{icao}")
    suspend fun getTaf(@Path("icao") icao: String, @Query("x-api-key") apiKey: String = this.apiKey): ResponseDto
}