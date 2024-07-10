package fr.serkox.metarviewer.data

import com.google.gson.GsonBuilder
import fr.serkox.metarviewer.data.repository.AirportRepository
import fr.serkox.metarviewer.data.repository.MetarRepository
import fr.serkox.metarviewer.data.repository.NetWorkTafRepository
import fr.serkox.metarviewer.data.repository.NetworkAirportRepository
import fr.serkox.metarviewer.data.repository.NetworkMetarRepository
import fr.serkox.metarviewer.data.repository.NetworkStationInfoRepository
import fr.serkox.metarviewer.data.repository.StationInfoRepository
import fr.serkox.metarviewer.data.repository.TafRepository
import fr.serkox.metarviewer.network.StationApiService
import fr.serkox.metarviewer.network.MetarApiService
import fr.serkox.metarviewer.network.TafApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val metarRepository: MetarRepository
    val airportRepository: AirportRepository
    val tafRepository: TafRepository
    val stationInfoRepository: StationInfoRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://api.checkwx.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitMetarService: MetarApiService by lazy {
        retrofit.create(MetarApiService::class.java)
    }

    private val retrofitTafService: TafApiService by lazy {
        retrofit.create(TafApiService::class.java)
    }

    private val retrofitStationInfoService: StationApiService by lazy {
        retrofit.create(StationApiService::class.java)
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

    override val stationInfoRepository: StationInfoRepository by lazy {
        NetworkStationInfoRepository(retrofitStationInfoService)
    }

}