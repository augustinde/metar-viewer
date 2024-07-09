package fr.serkox.metarviewer.data.repository

import fr.serkox.metarviewer.network.AirportInfoApiService

interface AirportInfoRepository{
    suspend fun getAirportInfo(id: String): String
}

class NetworkAirportInfoRepository(
    private val airportInfoApiService: AirportInfoApiService
): AirportInfoRepository{
    override suspend fun getAirportInfo(id: String): String {
        return airportInfoApiService.getAirportInfo(id)
    }
}