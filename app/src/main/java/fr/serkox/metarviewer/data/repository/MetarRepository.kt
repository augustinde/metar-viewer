package fr.serkox.metarviewer.data.repository

import fr.serkox.metarviewer.network.MetarApiService

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