package fr.serkox.androidproject.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TafApiService {

    @GET("taf.php")
    suspend fun getTaf(@Query("ids") ids: String): String
}