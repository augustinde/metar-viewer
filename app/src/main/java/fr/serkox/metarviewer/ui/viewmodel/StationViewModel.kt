package fr.serkox.metarviewer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.serkox.metarviewer.MetarApplication
import fr.serkox.metarviewer.data.repository.AirportInfoRepository
import fr.serkox.metarviewer.data.repository.MetarRepository
import fr.serkox.metarviewer.data.repository.TafRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class StationViewModel(
    private val metarRepository: MetarRepository,
    private val tafRepository: TafRepository,
    private val airportInfoRepository: AirportInfoRepository,
) : ViewModel() {
    var uiState: StationUiState by mutableStateOf(StationUiState.Loading)
        private set

    fun getMetar(id: String) {
        viewModelScope.launch {
            uiState = StationUiState.Loading
            uiState = try {
                StationUiState.Success(metarRepository.getMetar(id), tafRepository.getTaf(id), airportInfoRepository.getAirportInfo(id), id)
            }catch (e: IOException){
                Log.i("ERROR", e.toString())
                StationUiState.Error
            }catch (e: HttpException){
                Log.i("ERROR", e.toString())
                StationUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MetarApplication)
                val metarRepository = application.container.metarRepository
                val tafRepository = application.container.tafRepository
                val airportInfoRepository = application.container.airportInfoRepository
                StationViewModel(
                    metarRepository = metarRepository,
                    tafRepository = tafRepository,
                    airportInfoRepository = airportInfoRepository
                )
            }
        }
    }
}

sealed interface StationUiState {
    data class Success(val metar: String, val taf: String, val airportInfo: String, val airportCode: String) : StationUiState
    object Error : StationUiState
    object Loading : StationUiState
}
