package fr.serkox.metarviewer.network

import android.content.Context
import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import fr.serkox.metarviewer.data.model.AirfieldEntity
import fr.serkox.metarviewer.data.model.AirfieldObjectDto
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

        val airportWithMetar = listOf(
            "FMEE", "FMEP", "LFAC", "LFAQ", "LFAT", "LFBA", "LFBC", "LFBD", "LFBE", "LFBF",
            "LFBG", "LFBH", "LFBI", "LFBL", "LFBM", "LFBO", "LFBP", "LFBT", "LFBU", "LFBX",
            "LFBY", "LFBZ", "LFCK", "LFCR", "LFGA", "LFGJ", "LFHP", "LFJL", "LFJR", "LFKB",
            "LFKC", "LFKF", "LFKJ", "LFKS", "LFLB", "LFLC", "LFLH", "LFLL", "LFLN", "LFLP",
            "LFLS", "LFLU", "LFLV", "LFLW", "LFLX", "LFLY", "LFMC", "LFMD", "LFMH", "LFMI",
            "LFMK", "LFML", "LFMN", "LFMO", "LFMP", "LFMT", "LFMU", "LFMV", "LFMY", "LFOA",
            "LFOB", "LFOE", "LFOH", "LFOJ", "LFOK", "LFOP", "LFOT", "LFOV", "LFOZ", "LFPB",
            "LFPG", "LFPM", "LFPN", "LFPO", "LFPT", "LFPV", "LFQA", "LFQB", "LFQG", "LFQQ",
            "LFRB", "LFRC", "LFRD", "LFRG", "LFRH", "LFRI", "LFRJ", "LFRK", "LFRL", "LFRM",
            "LFRN", "LFRO", "LFRQ", "LFRS", "LFRT", "LFRU", "LFRV", "LFRZ", "LFSB", "LFSD",
            "LFSG", "LFSI", "LFSL", "LFSM", "LFSN", "LFSO", "LFST", "LFSX", "LFTH", "LFTW",
            "LFYR"
        )

        val listAirfieldType = object : TypeToken<List<AirfieldObjectDto>>() {}.type
        var list: List<AirfieldObjectDto> = Gson().fromJson(jsonString, listAirfieldType)
        list.map { item ->
            if(airportWithMetar.contains(item.ident)){
                db.collection("airports")
                    .document(item.ident)
                    .set(AirfieldEntity(item.latitude,item.longitude,item.ident, item.name))
            }
        }
    }
}