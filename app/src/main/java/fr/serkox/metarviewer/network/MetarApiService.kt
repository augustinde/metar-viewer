package fr.serkox.metarviewer.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MetarApiService{
    @GET("metar.php")
    suspend fun getMetar(@Query("ids") ids: String): String
}