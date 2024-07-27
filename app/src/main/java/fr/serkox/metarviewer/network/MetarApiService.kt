package fr.serkox.metarviewer.network

import fr.serkox.metarviewer.BuildConfig
import fr.serkox.metarviewer.data.model.dto.ResponseDecodedDto
import fr.serkox.metarviewer.data.model.dto.ResponseDto
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MetarApiService{

    private val apiKey: String
        get() = BuildConfig.WEATHER_API_KEY
    @GET("metar/{icao}")
    suspend fun getMetar(@Path("icao") icao: String, @Query("x-api-key") apiKey: String = this.apiKey): ResponseDto

    @GET("metar/{icao}/decoded")
    suspend fun getDecodedMetar(@Path("icao") icao: String, @Query("x-api-key") apiKey: String = this.apiKey): ResponseDecodedDto
}