package fr.serkox.androidproject.network

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import fr.serkox.androidproject.data.model.AirfieldEntity
import fr.serkox.androidproject.data.model.AirfieldObject
import fr.serkox.androidproject.data.model.AirfieldObjectDto
import java.io.IOException

class AddDataToFirestore {

    val db = Firebase.firestore

    fun addDataFromJson(context:Context){
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("airports/airports.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.e("error",ioException.toString())
        }

        val listAirfieldType = object : TypeToken<List<AirfieldObjectDto>>() {}.type
        var list: List<AirfieldObjectDto> = Gson().fromJson(jsonString, listAirfieldType)
        list.map { item ->
            db.collection("airports")
                .document(item.ident)
                .set(AirfieldEntity(GeoPoint(item.latitude,item.longitude),item.ident, item.name))

        }

    }
}