package fr.serkox.metarviewer.data.repository

import fr.serkox.metarviewer.data.model.dto.MetarDecodedDto
import fr.serkox.metarviewer.data.model.dto.MetarEncodedDto
import fr.serkox.metarviewer.network.MetarApiService

interface MetarRepository{
    suspend fun getMetar(icao: String): MetarEncodedDto
    suspend fun getMetarDecoded(icao: String): MetarDecodedDto
}

class NetworkMetarRepository(
    private val metarApiService: MetarApiService
): MetarRepository{

    override suspend fun getMetar(icao: String): MetarEncodedDto {
        val responseDto = metarApiService.getMetar(icao)
        return responseDto.toMetarEncodedDto()
    }

    override suspend fun getMetarDecoded(icao: String): MetarDecodedDto {
        val responseDto = metarApiService.getDecodedMetar(icao)
        return responseDto.toMetarDecodedDto()
    }

}