package fr.serkox.metarviewer.ui.viewmodel

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
import fr.serkox.metarviewer.MetarApplication
import fr.serkox.metarviewer.data.model.AirfieldObject
import fr.serkox.metarviewer.data.repository.AirportRepository
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

            mapUiState = try {
                MapUiState.Success(
                    airports,
                    CameraPosition.fromLatLngZoom(LatLng(48.866667, 2.333333), 8f)
                )
            }catch (e: Exception){
                MapUiState.Error
            }
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