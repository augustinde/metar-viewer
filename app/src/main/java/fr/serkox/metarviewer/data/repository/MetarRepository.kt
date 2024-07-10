package fr.serkox.metarviewer.data.repository

import fr.serkox.metarviewer.data.model.dto.MetarEncodedDto
import fr.serkox.metarviewer.network.MetarApiService

interface MetarRepository{
    suspend fun getMetar(icao: String): MetarEncodedDto
}

class NetworkMetarRepository(
    private val metarApiService: MetarApiService
): MetarRepository{

    override suspend fun getMetar(icao: String): MetarEncodedDto {
        val responseDto = metarApiService.getMetar(icao)
        return responseDto.toMetarEncodedDto()
    }

}