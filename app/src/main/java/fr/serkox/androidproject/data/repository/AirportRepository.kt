package fr.serkox.androidproject.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.serkox.androidproject.data.model.AirfieldObject
import kotlinx.coroutines.tasks.await
import java.util.Optional

interface AirportRepository{
    suspend fun getAll(): List<AirfieldObject>
}

class NetworkAirportRepository(): AirportRepository{
    /*override fun getAll(): List<AirfieldObject> {
        Log.e("ERROR", "AAAAAAAAAAAAAAA")
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var listAirport = mutableListOf<AirfieldObject>()
        db.collection("airports")
            .limit(100)
            .get()
            /*.addOnSuccessListener { document ->
                listAirport.add(
                    AirfieldObject(
                        LatLng(document.data?.get("latitude") as Double, document.data?.get("longitude") as Double),
                        document.data?.get("ident").toString(),
                        document.data?.get("name").toString()
                    )
                )
                Log.e("FIRESTORE", "SUCCESS " + listAirport.size)

            }*/
            .addOnSuccessListener { documents ->
                for(doc in documents.documents){
                    listAirport.add(
                        AirfieldObject(
                            LatLng(doc.data?.get("latitude") as Double, doc.data?.get("longitude") as Double),
                            doc.data?.get("ident").toString(),
                            doc.data?.get("name").toString()
                        )
                    )
                }
            }
          /*  for(doc in documents.result){
                //Log.e("ELT", doc.id + " " + doc.data?.get("latitude") as Double + " " + doc.data?.get("ident"))
                listAirport.add(
                    AirfieldObject(
                        LatLng(doc.data["latitude"] as Double, doc.data["longitude"] as Double),
                        doc.data["ident"].toString(),
                        doc.data["name"].toString()
                    )
                )
            }
            Log.e("FIRESTORE", "COMPLETE " + listAirport.size)*/
        return listAirport

    }*/

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
/*
class AirfieldRepository{

   fun getAirfieldList(): List<AirfieldObject>{
       return listOf(
           AirfieldObject("LFLV", "Vichy-Charmeil Airport", 46.169700622558594, 3.4037399291992188),
           AirfieldObject("LFLN", "Saint-Yan Airport", 46.412498474121094, 4.0132598876953125),
           AirfieldObject("LFSD", "Dijon-Bourgogne Airport", 47.268902, 5.09),
           AirfieldObject("LFGJ", "Dole-Tavaux Airport", 47.042686, 5.435063),
           AirfieldObject("LFQG", "Nevers-Fourchambault Airport", 47.002601623535156, 3.1133298873901367),
           AirfieldObject("LFLA", "Auxerre-Branches Airport", 47.85020065307617, 3.497109889984131),
           AirfieldObject("LFSM", "Montbéliard-Courcelles Airfield", 47.487, 6.79054),
           AirfieldObject("LFSG", "Épinal-Mirecourt Airport", 48.325001, 6.06998),
           AirfieldObject("LFSB", "EuroAirport Basel-Mulhouse-Freiburg Airport", 47.59, 7.529167),
           AirfieldObject("LFGA", "Colmar-Houssen Airport", 48.109901428222656, 7.359010219573975),
           AirfieldObject("LFST", "Strasbourg Airport", 48.538299560546875, 7.628230094909668),
           AirfieldObject("LFQA", "Aérodrome de Reims Prunay", 49.208698, 4.15658),
           AirfieldObject("LFOK", "Châlons-Vatry Airport", 48.773333, 4.206111),
           AirfieldObject("LFKB", "Bastia-Poretta Airport", 42.55270004272461, 9.48373031616211),
           AirfieldObject("LFKC", "Calvi-Sainte-Catherine Airport", 42.5244444, 8.7930556),
           AirfieldObject("LFKJ", "Ajaccio-Napoléon Bonaparte Airport", 41.92359924316406, 8.8029203414917),
           AirfieldObject("LFKS", "Solenzara (BA 126) Air Base", 41.924400329589844, 9.406000137329102),
           AirfieldObject("LFKF", "Figari Sud-Corse Airport", 41.500599, 9.09778),
           AirfieldObject("LFTH", "Toulon-Hyères Airport", 43.0973014832, 6.14602994919),
           AirfieldObject("LFCR", "Rodez-Marcillac Airport", 44.407901763916016, 2.4826700687408447),
           AirfieldObject("LFBM", "Mont-de-Marsan (BA 118) Air Base", 43.911701, -0.5075),
           AirfieldObject("LFBZ", "Biarritz-Anglet-Bayonne Airport", 43.4683333, -1.5311111),
           AirfieldObject("LFBP", "Pau Pyrénées Airport", 43.380001068115234, -0.41861099004745483),
           AirfieldObject("LFBT", "Tarbes-Lourdes-Pyrénées Airport", 43.1786994934082, -0.006438999902456999),
           AirfieldObject("LFBO", "Toulouse-Blagnac Airport", 43.629101, 1.36382),
           AirfieldObject("LFCK", "Castres-Mazamet Airport", 43.55630111694336, 2.289180040359497),
           AirfieldObject("LFMK", "Carcassonne Airport", 43.215999603271484, 2.3063199520111084),
           AirfieldObject("LFMP", "Perpignan-Rivesaltes (Llabanère) Airport", 42.74039840698242, 2.8706700801849365),
           AirfieldObject("LFMU", "Béziers-Vias Airport", 43.32350158691406, 3.3538999557495117),
           AirfieldObject("LFMT", "Montpellier-Méditerranée Airport", 43.57619857788086, 3.96301007270813),
           AirfieldObject("LFMV", "Avignon-Caumont Airport", 43.90729904174805, 4.901830196380615),
           AirfieldObject("LFMO", "Orange-Caritat (BA 115) Air Base", 44.140499, 4.86672),
           AirfieldObject("LFML", "Marseille Provence Airport", 43.439271922, 5.22142410278),
           AirfieldObject("LFTW", "Nîmes-Arles-Camargue Airport", 43.75740051269531, 4.4163498878479),
           AirfieldObject("LFMD", "Cannes-Mandelieu Airport", 43.542, 6.95348),
           AirfieldObject("LFMN", "Nice-Côte d'Azur Airport", 43.658401, 7.21587),
           AirfieldObject("LFLU", "Valence-Chabeuil Airport", 44.9216, 4.9699),
           AirfieldObject("LFLS", "Grenoble-Isère Airport", 45.3629, 5.32937),
           AirfieldObject("LFLL", "Lyon Saint-Exupéry Airport", 45.725556, 5.081111),
           AirfieldObject("LFLB", "Chambéry-Savoie Airport", 45.638099670410156, 5.880229949951172),
           AirfieldObject("LFLP", "Annecy-Haute-Savoie-Mont Blanc Airport", 45.9308333, 6.1063889),
           AirfieldObject("LFMH", "Saint-Étienne-Bouthéon Airport", 45.54059982299805, 4.296390056610107),
           AirfieldObject("LFHP", "Le Puy-Loudes Airfield", 45.0807, 3.76289),
           AirfieldObject("LFLC", "Clermont-Ferrand Auvergne Airport", 45.7867012024, 3.1691699028),
           AirfieldObject("LFRT", "Saint-Brieuc-Armor Airport", 48.5378, -2.85444),
           AirfieldObject("LFRD", "Dinard-Pleurtuit-Saint-Malo Airport", 48.58769989013672, -2.0799601078033447),
           AirfieldObject("LFRO", "Lannion-Côte de Granit Airport", 48.754398, -3.47166),
           AirfieldObject("LFRU", "Morlaix-Ploujean Airport", 48.6031990051, -3.81577992439),
           AirfieldObject("LFRJ", "Landivisiau Air Base", 48.53030014038086, -4.151639938354492),
           AirfieldObject("LFRB", "Brest Bretagne Airport", 48.447898864746094, -4.418540000915527),
           AirfieldObject("LFRH", "Lorient South Brittany (Bretagne Sud) Airport", 47.76060104370117, -3.440000057220459),
           AirfieldObject("LFRQ", "Quimper-Cornouaille Airport", 47.974998474121094, -4.167789936065674),
           AirfieldObject("LFRI", "La Roche-sur-Yon Airport", 46.701900482177734, -1.3786300420761108),
           AirfieldObject("LFOT", "Tours-Val-de-Loire Airport", 47.4322013855, 0.727605998516),
           AirfieldObject("LFYR", "Romorantin Pruniers Airfield", 47.317543, 1.691036),
           AirfieldObject("LFLX", "Châteauroux-Déols \"Marcel Dassault\" Airport", 46.860278, 1.721111),
           AirfieldObject("LFBI", "Poitiers-Biard Airport", 46.58769989013672, 0.30666598677635193),
           AirfieldObject("LFBH", "La Rochelle-Île de Ré Airport", 46.17919921875, -1.1952799558639526),
           AirfieldObject("LFBL", "Limoges Airport", 45.86280059814453, 1.1794400215148926),
           AirfieldObject("LFBU", "Angoulême-Brie-Champniers Airport", 45.729198, 0.221456),
           AirfieldObject("LFBG", "Cognac-Châteaubernard (BA 709) Air Base", 45.65829849243164, -0.3174999952316284),
           AirfieldObject("LFBX", "Périgueux-Bassillac Airport", 45.19810104370117, 0.815555989742279),
           AirfieldObject("LFBE", "Bergerac-Roumanière Airport", 44.82529830932617, 0.5186110138893127),
           AirfieldObject("LFBD", "Bordeaux-Mérignac Airport", 44.8283, -0.715556),
           AirfieldObject("LFBA", "Agen-La Garenne Airport", 44.17470169067383, 0.5905560255050659),
           AirfieldObject("LFSL", "Brive-Souillac", 45.039722, 1.485556),
           AirfieldObject("LFLW", "Aurillac Airport", 44.89139938354492, 2.4219400882720947),
           AirfieldObject("LFQQ", "Lille-Lesquin Airport", 50.563332, 3.086886),
           AirfieldObject("LFAC", "Calais-Dunkerque Airport", 50.962101, 1.95476),
           AirfieldObject("LFAT", "Le Touquet-Côte d'Opale Airport", 50.517399, 1.62059),
           AirfieldObject("LFAQ", "Albert-Bray Airport", 49.9715003967, 2.69765996933),
           AirfieldObject("LFOB", "Paris Beauvais Tillé Airport", 49.454399, 2.11278),
           AirfieldObject("LFOP", "Rouen Vallée de Seine Airport", 49.386674, 1.183519),
           AirfieldObject("LFOH", "Le Havre-Octeville Airport", 49.534038, 0.088406),
           AirfieldObject("LFRG", "Deauville-Saint-Gatien Airport", 49.365299, 0.154306),
           AirfieldObject("LFRK", "Caen-Carpiquet Airport", 49.173302, -0.45),
           AirfieldObject("LFOE", "Évreux-Fauville (BA 105) Air Base", 49.02870178222656, 1.2198599576950073),
           AirfieldObject("LFPT", "Aérodrome de Pontoise - Cormeilles en Vexin", 49.096667, 2.040833),
           AirfieldObject("LFPG", "Charles de Gaulle International Airport", 49.012798, 2.55),
           AirfieldObject("LFPN", "Toussus-le-Noble Airport", 48.7519, 2.10619),
           AirfieldObject("LFPO", "Paris-Orly Airport", 48.72333, 2.37944),
           AirfieldObject("LFPM", "Melun-Villaroche Air Base", 48.604698181152344, 2.6711199283599854),
           AirfieldObject("LFOJ", "Orléans-Bricy (BA 123) Air Base", 47.987801, 1.76056),
           AirfieldObject("LFOZ", "Orléans – Saint-Denis-de-l'Hôtel Airport", 47.8969, 2.16333),
           AirfieldObject("LFRM", "Le Mans-Arnage Airport", 47.94860076904297, 0.20166699588298798),
           AirfieldObject("LFJR", "Angers-Loire Airport", 47.560299, -0.312222),
           AirfieldObject("LFRC", "Cherbourg-Maupertus Airport", 49.65010070800781, -1.4702800512313843),
           AirfieldObject("LFRS", "Nantes Atlantique Airport", 47.153198242200006, -1.61073005199),
           AirfieldObject("LFRV", "Vannes-Meucon Airport", 47.72330093383789, -2.718559980392456),
           AirfieldObject("LFRH", "Lorient South Brittany (Bretagne Sud) Airport", 47.76060104370117, -3.440000057220459)
       )
   }

}*/