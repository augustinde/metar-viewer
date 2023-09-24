package fr.serkox.androidproject.data.repository

import fr.serkox.androidproject.network.TafApiService

interface TafRepository {
    suspend fun getTaf(id: String): String
}

class NetWorkTafRepository(
    private val tafApiService: TafApiService
): TafRepository {
    override suspend fun getTaf(id: String): String {
        return tafApiService.getTaf(id)
    }
}