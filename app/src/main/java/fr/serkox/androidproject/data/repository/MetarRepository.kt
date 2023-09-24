package fr.serkox.androidproject.data.repository

import fr.serkox.androidproject.network.MetarApiService

interface MetarRepository{
    suspend fun getMetar(id: String): String
}

class NetworkMetarRepository(
    private val metarApiService: MetarApiService
): MetarRepository{

    override suspend fun getMetar(id: String): String {
        return metarApiService.getMetar(id)
    }

}