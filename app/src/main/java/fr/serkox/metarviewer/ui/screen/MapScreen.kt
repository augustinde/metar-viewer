package fr.serkox.metarviewer.ui.screen

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import fr.serkox.metarviewer.R
import fr.serkox.metarviewer.data.model.AirfieldObject
import fr.serkox.metarviewer.network.AddDataToFirestore
import fr.serkox.metarviewer.ui.navigation.MetarViewerNavHost
import fr.serkox.metarviewer.ui.navigation.MetarViewerNavigationScreen
import fr.serkox.metarviewer.ui.reusable.LoadingAnimation
import fr.serkox.metarviewer.ui.viewmodel.MapUiState
import fr.serkox.metarviewer.ui.viewmodel.MapViewModel
import fr.serkox.metarviewer.ui.viewmodel.StationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetarViewerAppBar(
    currentScreen: MetarViewerNavigationScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetarViewerApp(){
    val stationViewModel: StationViewModel = viewModel(factory = StationViewModel.Factory)
    val mapViewModel: MapViewModel = viewModel(factory = MapViewModel.Factory)
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MetarViewerNavigationScreen.valueOf(backStackEntry?.destination?.route ?: MetarViewerNavigationScreen.Start.name)
    val service = AddDataToFirestore()
    val context: Context = LocalContext.current
    Scaffold(
        topBar = {
            MetarViewerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){
        innerPadding ->
        MetarViewerNavHost(
            stationViewModel = stationViewModel,
            mapViewModel = mapViewModel,
            navController = navController,
            startDestination = MetarViewerNavigationScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
       //Button(onClick = { service.addDataFromJson(context = context) }, content = {Text(text = "Click")}, modifier = Modifier.padding(innerPadding))
    }
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)@Composable
fun MapScreen(
    mapUiState: MapUiState,
    onMarkerClicked: (String) -> Unit,
){

    when(mapUiState) {
        is MapUiState.Success -> Map(mapUiState.airfieldList, mapUiState.cameraPosition, onMarkerClicked)
        is MapUiState.Loading -> LoadingMap()
        is MapUiState.Error -> ErrorMap()
    }

}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun Map(
    airfieldList: List<AirfieldObject>,
    cameraPosition: CameraPosition,
    onMarkerClicked: (String) -> Unit
){
    val items = remember { mutableStateListOf<AirfieldObject>() }
    airfieldList.map { airfieldObject ->
        items.add(airfieldObject)
    }
    GoogleMap(
        cameraPositionState = rememberCameraPositionState{
            position = cameraPosition
        },
    ) {
        Clustering(
            items = items,
            onClusterItemClick = {
                onMarkerClicked(it.title)
                false
            },
            clusterItemContent = null
        )
    }

}

@Composable
fun LoadingMap(){
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
fun ErrorMap(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Erreur lors du chargement du metar !")
    }}

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
