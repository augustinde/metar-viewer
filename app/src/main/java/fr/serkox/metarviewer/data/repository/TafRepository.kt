package fr.serkox.metarviewer.data.repository

import fr.serkox.metarviewer.data.model.dto.TafEncodedDto
import fr.serkox.metarviewer.network.TafApiService

interface TafRepository {
    suspend fun getTaf(icao: String): TafEncodedDto
}

class NetWorkTafRepository(
    private val tafApiService: TafApiService
): TafRepository {
    override suspend fun getTaf(icao: String): TafEncodedDto {
        val responseDto = tafApiService.getTaf(icao)
        return responseDto.toTafEncodedDto()
    }
}