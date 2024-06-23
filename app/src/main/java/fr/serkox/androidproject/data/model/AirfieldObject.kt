package fr.serkox.androidproject.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class AirfieldObject(
    val pos: LatLng,
    val ident: String,
    val name: String,
): ClusterItem {
    override fun getPosition(): LatLng = pos
    override fun getTitle(): String = ident
    override fun getSnippet(): String = name

}

data class AirfieldObjectDto(
    val ident: String,
    val name: String,
    @SerializedName("latitude_deg")
    val latitude: Double,
    @SerializedName("longitude_deg")
    val longitude: Double,
)

data class AirfieldEntity(
    val latitude: Double,
    val longitude: Double,
    val ident: String? = "",
    val name: String? = "",
)