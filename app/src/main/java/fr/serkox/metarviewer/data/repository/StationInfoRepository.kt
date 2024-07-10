package fr.serkox.metarviewer.data.repository

import android.util.Log
import fr.serkox.metarviewer.data.model.dto.StationDto
import fr.serkox.metarviewer.network.StationApiService

interface StationInfoRepository{
    suspend fun getStationInfo(icao: String): StationDto
}

class NetworkStationInfoRepository(
    private val stationApiService: StationApiService
): StationInfoRepository{
    override suspend fun getStationInfo(icao: String) : StationDto{
        val responseDto = stationApiService.getStationInfo(icao)
        return responseDto.toStationDto()
    }
}