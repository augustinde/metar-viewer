package fr.serkox.metarviewer.data.model.dto

data class ResponseDto(
    val results: Int,
    val data: List<Any>
){
    fun toMetarEncodedDto(): MetarEncodedDto{
        return MetarEncodedDto(
            metar = data[0].toString()
        )
    }

    fun toTafEncodedDto(): TafEncodedDto{
        return TafEncodedDto(
            taf = data.firstOrNull()?.toString() ?: ""
        )
    }
}