package fr.serkox.metarviewer.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AirportInfoApiService {

    @GET("airport.php")
    suspend fun getAirportInfo(@Query("ids") ids: String): String
}