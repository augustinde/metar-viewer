package fr.serkox.androidproject.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import fr.serkox.androidproject.MainActivity
import fr.serkox.androidproject.R
import fr.serkox.androidproject.data.repository.AirfieldRepository
import fr.serkox.androidproject.ui.theme.AndroidProjectTheme


@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)@Composable
fun MapScreen(
    onButtonClick: () -> Unit
){
    val airfieldRepository: AirfieldRepository by lazy { AirfieldRepository() }
    val context = LocalContext.current

    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }
    var deviceLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 8f)
    }
    val locationResult = fusedLocationProviderClient.lastLocation
    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            lastKnownLocation = task.result
            deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 8f)
        } else {
            Log.e("TAG", "Exception: %s", task.exception)
        }
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        val listAirfield = airfieldRepository.getAirfieldList()
        listAirfield.map { airfieldObject ->

            Marker(
                onClick = {
                    Log.i("MARKER_CLICK", airfieldObject.ident)
                    onButtonClick()
                    false
                },
                state = MarkerState(position = LatLng(airfieldObject.latitude, airfieldObject.longitude)),
                title = airfieldObject.ident,
                snippet = airfieldObject.name,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)

            )
            Marker(
                state = MarkerState(position = deviceLatLng),
                icon = bitmapDescriptorFromVector(context, R.drawable.plane),
                anchor = Offset(0.5f,0.5f),
            )
        }

    }
}

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

@SuppressLint("MissingPermission")
@Preview
@Composable
fun MapScreenPreview(){
    AndroidProjectTheme {
        MapScreen({})
    }
}