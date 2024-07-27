package fr.serkox.metarviewer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.serkox.metarviewer.R
import fr.serkox.metarviewer.ui.screen.MapScreen
import fr.serkox.metarviewer.ui.screen.StationScreen
import fr.serkox.metarviewer.ui.viewmodel.MapViewModel
import fr.serkox.metarviewer.ui.viewmodel.StationViewModel

enum class MetarViewerNavigationScreen(@StringRes val title: Int){
    Start(title = R.string.start),
    Metar(title = R.string.metar)
}

@Composable
fun MetarViewerNavHost(
    stationViewModel: StationViewModel,
    mapViewModel: MapViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = MetarViewerNavigationScreen.Start.name){
            MapScreen(
                mapUiState = mapViewModel.mapUiState,
                onMarkerClicked = {
                    stationViewModel.getMetar(it)
                    navController.navigate(MetarViewerNavigationScreen.Metar.name)
                },
            )
        }
        composable(route = MetarViewerNavigationScreen.Metar.name){
            StationScreen(
                stationUiState = stationViewModel.uiState,
                stationViewModel = stationViewModel
            )
        }

    }
}