package fr.serkox.metarviewer.data.model.dto

data class ResponseStationDto(
    val results: Int,
    val data: List<StationDto>
){

    fun toStationDto(): StationDto {
        return data[0]
    }
}