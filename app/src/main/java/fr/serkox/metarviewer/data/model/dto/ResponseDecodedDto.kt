package fr.serkox.metarviewer.data.model.dto


data class ResponseDecodedDto(
    val results: Int,
    val data: List<MetarDecodedDto>
){
    fun toMetarDecodedDto(): MetarDecodedDto{
        return data[0]
    }
}