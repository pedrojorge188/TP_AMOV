package pt.isec.amov.ui.composes.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapViewContainer(mapView: MapView) {
    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            mapView?.let {
                it.onCreate(null)
                it.getMapAsync { googleMap ->
                    setupMap(googleMap)
                }
            }
        }
    )
}

private fun setupMap(googleMap: GoogleMap) {
    // Configurações iniciais do mapa
    val initialLocation = LatLng(-34.0, 151.0)
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))

    // Adiciona um marcador
    val markerOptions = MarkerOptions().position(initialLocation).title("Marcador no Mapa")
    googleMap.addMarker(markerOptions)
}

@Composable
fun rememberMapView(): MutableState<MapView?> {
    return remember { mutableStateOf(null) }
}