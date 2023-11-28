package pt.isec.amov.ui.screens.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun LocationMapScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    location: Location
) {


    val geoPoint by remember{ mutableStateOf(
        GeoPoint(
            location.latitude, location.longitude
        )
    ) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = location.name,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )

        MapScene(POI = location.pointsOfInterest, geoPoint = geoPoint)

    }
}

@Composable
fun MapScene(POI : List<PointOfInterest>? , geoPoint : GeoPoint) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .clipToBounds()
            .background(Color(255, 240, 128)),
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK);
                    setMultiTouchControls(true)
                    controller.setCenter(geoPoint)
                    controller.setZoom(18.0)
                    if(POI != null)
                        for(poi in POI)
                            overlays.add(
                                Marker(this).apply {
                                    position = GeoPoint(poi.latitude,poi.longitude)
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    title = poi.name
                                },
                            )
                }
            },
            update = { view ->
                view.controller.setCenter(geoPoint)
            }
        )
    }

}