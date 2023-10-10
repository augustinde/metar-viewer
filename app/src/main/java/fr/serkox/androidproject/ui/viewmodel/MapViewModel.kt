package fr.serkox.androidproject.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import fr.serkox.androidproject.MetarApplication
import fr.serkox.androidproject.data.model.AirfieldObject
import fr.serkox.androidproject.data.repository.AirportRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MapViewModel(private val airportRepository: AirportRepository): ViewModel() {

    var mapUiState: MapUiState by mutableStateOf(MapUiState.Loading)
        private set

    init {
        getAirfields()
    }

    private fun getAirfields(){
        viewModelScope.launch {
            mapUiState = MapUiState.Loading

            val airports: List<AirfieldObject> = airportRepository.getAll();

            Log.i("SIZE", airports.size.toString())
            mapUiState = try {
                MapUiState.Success(
                    airports,
                    CameraPosition.fromLatLngZoom(LatLng(49.9715003967, 2.69765996933), 8f)
                )
            }catch (e: Exception){
                Log.e("ERROR", e.toString())
                MapUiState.Error
            }
            Log.i("tag", "zdzdpazdj")

        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetarApplication)
                val airportRepository = application.container.airportRepository
                MapViewModel(airportRepository = airportRepository)
            }
        }
    }

}

sealed interface MapUiState {
    data class Success(val airfieldList: List<AirfieldObject>, val cameraPosition: CameraPosition) : MapUiState
    object Error: MapUiState
    object Loading: MapUiState
}