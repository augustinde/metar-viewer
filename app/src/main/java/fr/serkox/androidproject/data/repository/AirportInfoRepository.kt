package fr.serkox.androidproject.data.repository

import fr.serkox.androidproject.network.AirportInfoApiService

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