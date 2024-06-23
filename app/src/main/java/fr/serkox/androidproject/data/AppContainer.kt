package fr.serkox.androidproject.data

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import fr.serkox.androidproject.data.repository.AirportInfoRepository
import fr.serkox.androidproject.data.repository.AirportRepository
import fr.serkox.androidproject.data.repository.MetarRepository
import fr.serkox.androidproject.data.repository.NetWorkTafRepository
import fr.serkox.androidproject.data.repository.NetworkAirportInfoRepository
import fr.serkox.androidproject.data.repository.NetworkAirportRepository
import fr.serkox.androidproject.data.repository.NetworkMetarRepository
import fr.serkox.androidproject.data.repository.TafRepository
import fr.serkox.androidproject.network.AirportInfoApiService
import fr.serkox.androidproject.network.MetarApiService
import fr.serkox.androidproject.network.TafApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val metarRepository: MetarRepository
    val airportRepository: AirportRepository
    val tafRepository: TafRepository
    val airportInfoRepository: AirportInfoRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://beta.aviationweather.gov/cgi-bin/data/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitMetarService: MetarApiService by lazy {
        retrofit.create(MetarApiService::class.java)
    }

    private val retrofitTafService: TafApiService by lazy {
        retrofit.create(TafApiService::class.java)
    }

    private val retrofitAirportInfoService: AirportInfoApiService by lazy {
        retrofit.create(AirportInfoApiService::class.java)
    }

    override val airportRepository: AirportRepository by lazy {
        NetworkAirportRepository()
    }

    override val metarRepository: MetarRepository by lazy {
        NetworkMetarRepository(retrofitMetarService)
    }

    override val tafRepository: TafRepository by lazy {
        NetWorkTafRepository(retrofitTafService)
    }

    override val airportInfoRepository: AirportInfoRepository by lazy {
        NetworkAirportInfoRepository(retrofitAirportInfoService)
    }

}