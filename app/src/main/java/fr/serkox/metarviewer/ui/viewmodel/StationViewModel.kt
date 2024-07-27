package fr.serkox.metarviewer.ui.viewmodel

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
import fr.serkox.metarviewer.data.model.dto.MetarDecodedDto
import fr.serkox.metarviewer.data.model.dto.StationDto
import fr.serkox.metarviewer.data.repository.MetarRepository
import fr.serkox.metarviewer.data.repository.StationInfoRepository
import fr.serkox.metarviewer.data.repository.TafRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class StationViewModel(
    private val metarRepository: MetarRepository,
    private val tafRepository: TafRepository,
    private val stationInfoRepository: StationInfoRepository,
) : ViewModel() {
    var uiState: StationUiState by mutableStateOf(StationUiState.Loading)
        private set

    fun getMetar(icao: String) {
        viewModelScope.launch {
            uiState = StationUiState.Loading
            uiState = try {
                StationUiState.Success(metarRepository.getMetar(icao).metar, tafRepository.getTaf(icao).taf, stationInfoRepository.getStationInfo(icao))
            }catch (e: IOException){
                StationUiState.Error
            }catch (e: HttpException){
                StationUiState.Error
            }
        }
    }

    suspend fun decodeMetar(icao: String): MetarDecodedDto {
        return metarRepository.getMetarDecoded(icao)
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MetarApplication)
                val metarRepository = application.container.metarRepository
                val tafRepository = application.container.tafRepository
                val stationInfoRepository = application.container.stationInfoRepository
                StationViewModel(
                    metarRepository = metarRepository,
                    tafRepository = tafRepository,
                    stationInfoRepository = stationInfoRepository
                )
            }
        }
    }
}

sealed interface StationUiState {
    data class Success(val metar: String? = "", val taf: String? = "", val stationInfo: StationDto? = null, val decodedMetar: MetarDecodedDto? = null) : StationUiState
    object Error : StationUiState
    object Loading : StationUiState
}
