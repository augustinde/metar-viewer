package fr.serkox.metarviewer.data

import fr.serkox.metarviewer.data.repository.AirportInfoRepository
import fr.serkox.metarviewer.data.repository.AirportRepository
import fr.serkox.metarviewer.data.repository.MetarRepository
import fr.serkox.metarviewer.data.repository.NetWorkTafRepository
import fr.serkox.metarviewer.data.repository.NetworkAirportInfoRepository
import fr.serkox.metarviewer.data.repository.NetworkAirportRepository
import fr.serkox.metarviewer.data.repository.NetworkMetarRepository
import fr.serkox.metarviewer.data.repository.TafRepository
import fr.serkox.metarviewer.network.AirportInfoApiService
import fr.serkox.metarviewer.network.MetarApiService
import fr.serkox.metarviewer.network.TafApiService
import retrofit2.Retrofit
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