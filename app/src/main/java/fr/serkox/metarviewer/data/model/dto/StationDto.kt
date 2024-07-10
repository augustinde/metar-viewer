package fr.serkox.metarviewer.data.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StationDto(
    @SerializedName("icao") @Expose val icao: String,
    @SerializedName("elevation") @Expose val elevation: ElevationDto,
    @SerializedName("latitude") @Expose val latitude: LatitudeDto,
    @SerializedName("longitude") @Expose val longitude: LongitudeDto,
    @SerializedName("location") @Expose val location: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("type") @Expose val type: String,
)

data class ElevationDto(
    val feet: Int,
    val meters: Int
)

data class LatitudeDto(
    val decimal: Double,
    val degrees: String,
)

data class LongitudeDto(
    val decimal: Double,
    val degrees: String,
)