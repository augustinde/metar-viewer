package fr.serkox.metarviewer.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.serkox.metarviewer.R
import fr.serkox.metarviewer.data.model.dto.StationDto
import fr.serkox.metarviewer.ui.reusable.LoadingAnimation
import fr.serkox.metarviewer.ui.viewmodel.StationUiState

@Composable
fun StationScreen(
    stationUiState: StationUiState
){
    when(stationUiState){
        is StationUiState.Loading -> LoadingInfo()
        is StationUiState.Error -> ErrorInfo()
        is StationUiState.Success -> StationInfo(
            metar = stationUiState.metar,
            taf = stationUiState.taf,
            stationInfo = stationUiState.stationInfo
        )
    }

}

@Composable
fun StationInfo(
    metar: String,
    taf: String,
    stationInfo: StationDto
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 20.dp, 20.dp, 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        StationInfoCard(station = stationInfo)
        WeatherInfoCard(value = metar, type = "METAR")
        WeatherInfoCard(value = taf, type = "TAF")
    }
}


@Composable
fun StationInfoCard(station: StationDto) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
        if (station.icao.isEmpty()) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = stringResource(R.string.information_not_available),
                color = Color.White
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = station.icao, style = MaterialTheme.typography.titleMedium)
                Text(text = station.location, style = MaterialTheme.typography.bodyMedium)
                Text(text = station.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.station_status, station.status), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.station_type, station.type), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(
                    R.string.meters_feet,
                    station.elevation.meters,
                    station.elevation.feet
                ), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.latitude, station.latitude.decimal), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.longitude, station.longitude.decimal), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun WeatherInfoCard(
    value: String,
    type: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = type)

            if (value.isNotEmpty()) {
                Text(modifier = Modifier.padding(12.dp), text = value, color = Color.White)
            } else {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(R.string.information_not_available),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun LoadingInfo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingAnimation()
    }
}

@Composable
fun ErrorInfo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.error_loading_information))
    }
}