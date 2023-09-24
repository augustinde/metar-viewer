package fr.serkox.androidproject.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.serkox.androidproject.ui.reusable.LoadingAnimation
import fr.serkox.androidproject.ui.theme.AndroidProjectTheme
import fr.serkox.androidproject.ui.viewmodel.StationUiState

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
            airportInfo = stationUiState.airportInfo
        )
    }

}

@Composable
fun StationInfo(
    metar: String,
    taf: String,
    airportInfo: String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp,20.dp,20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(modifier = Modifier.padding(0.dp, 10.dp), text = "INFO")
        StationInfoCard(value = airportInfo)
        Text(text = "METAR")
        StationInfoCard(value = metar)
        Text(text = "TAF")
        StationInfoCard(value = taf)
    }
}


@Composable
fun StationInfoCard(value: String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)

    ){
        if(value.isNotEmpty()){
            Text(modifier = Modifier.padding(12.dp), text = value)
        }else{
            Text(modifier = Modifier.padding(12.dp), text = "Cette information n'est pas disponible.")
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
        Text(text = "Erreur lors du chargement des informations !")
    }
}

@Composable
@Preview
fun MetarScreenPreview(){
    AndroidProjectTheme {
    }
}