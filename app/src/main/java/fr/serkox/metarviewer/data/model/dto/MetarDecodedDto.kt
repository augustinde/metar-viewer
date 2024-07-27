package fr.serkox.metarviewer.data.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MetarDecodedDto(
    @SerializedName("icao") @Expose val icao: String,
    @SerializedName("barometer") @Expose val barometer: BarometerDto,
    @SerializedName("ceiling") @Expose val ceiling: CeilingDto,
    @SerializedName("clouds") @Expose val clouds: List<CloudDto>,
    @SerializedName("conditions") @Expose val conditions: List<ConditionDto>,
    @SerializedName("dewpoint") @Expose val dewpoint: TemperatureDto,
    @SerializedName("temperature") @Expose val temperature: TemperatureDto,
    @SerializedName("elevation") @Expose val elevation: ElevationDto,
    @SerializedName("flight_category") @Expose val flightCategory: String,
    @SerializedName("observed") @Expose val observed: String,
    @SerializedName("humidity") @Expose val humidity: HumidityDto,
    @SerializedName("station") @Expose val station: StationMetarDto,
    @SerializedName("visibility") @Expose val visibility: VisibilityDto,
    @SerializedName("wind") @Expose val wind: WindDto,
)

data class BarometerDto(
    val hpa: Double? = null,
)

data class CeilingDto(
    val feet: Int? = null,
    val meters: Int? = null,
)

data class CloudDto(
    val code: String? = null,
    val text: String? = null,
    val feet: Int? = null,
    val meters: Int? = null,
)

data class ConditionDto(
    val code: String? = null,
    val text: String? = null,
)

data class HumidityDto(
    val percent: Int? = null,
)

data class StationMetarDto(
    val location: String? = null,
    val name: String? = null,
    val type: String? = null,
)

data class TemperatureDto(
    val celsius: Int? = null,
)

data class VisibilityDto(
    val meters: String? = null,
)

data class WindDto(
    val degrees: Int,
    @SerializedName("speed_kph") val speedKph: Int? = null,
    @SerializedName("speed_kts") val speedKts: Int? = null,
    @SerializedName("gust_kph") val gustKph: Int? = null,
    @SerializedName("gust_kts") val gustKts: Int? = null,
)
