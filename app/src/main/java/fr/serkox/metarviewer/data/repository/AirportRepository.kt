package fr.serkox.metarviewer.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import fr.serkox.metarviewer.data.model.AirfieldObject
import kotlinx.coroutines.tasks.await

interface AirportRepository{
    suspend fun getAll(): List<AirfieldObject>
}

class NetworkAirportRepository(): AirportRepository{

    override suspend fun getAll(): List<AirfieldObject> {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val airports = mutableListOf<AirfieldObject>()

        try {
            val documents = db.collection("airports")
                .get()
                .await()

            for (doc in documents.documents) {
                airports.add(
                    AirfieldObject(
                        LatLng(doc.data?.get("latitude") as Double, doc.data?.get("longitude") as Double),
                        doc.data?.get("ident").toString(),
                        doc.data?.get("name").toString()
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }

        return airports
    }
}