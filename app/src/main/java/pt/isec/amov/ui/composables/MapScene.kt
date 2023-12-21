@file:Suppress("DEPRECATION")

package pt.isec.amov.ui.composables

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import pt.isec.amov.R
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun MapScene(POI: List<PointOfInterest>?, geoPoint: GeoPoint, location: Boolean, vm: ActionsViewModel) {
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
                    controller.setZoom(16.0)

                    if (location) {
                        val points = createCirclePoints(geoPoint, 15_000.0)
                        val circle = Polygon().apply {
                            points.forEach { addPoint(it) }
                            strokeWidth = 5f
                            strokeColor = R.color.blue
                        }
                        overlays.add(circle)
                    }

                    overlays.add(
                        Marker(this).apply {
                            position = GeoPoint(vm.currentLocation.value!!.latitude, vm.currentLocation.value!!.longitude)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                            title = vm.user.value!!.email
                        },
                    )

                    if (POI != null)
                        for (poi in POI) {
                            val imageName = vm.getCategoryIcon(poi.category ?: "")
                            val int= getResourceIdForImage(imageName)
                            if (int != 0) {
                                val bitmap = BitmapFactory.decodeResource(context.resources, int ?: 0)
                                val width = 120
                                val height = 120
                                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
                                val marker = Marker(this).apply {
                                    position = GeoPoint(poi.latitude, poi.longitude)
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                    title = poi.name
                                    snippet = poi.description
                                    icon = BitmapDrawable(context.resources, scaledBitmap)
                                }
                                overlays.add(marker)
                            } else {
                                val originalDrawable = ContextCompat.getDrawable(context, org.osmdroid.library.R.drawable.ic_menu_mylocation)
                                val color = ContextCompat.getColor(context, R.color.red)
                                originalDrawable?.let {
                                    val mutableDrawable = DrawableCompat.wrap(it.mutate())
                                    DrawableCompat.setTint(mutableDrawable, color)
                                }
                                overlays.add(
                                    Marker(this).apply {
                                        position = GeoPoint(poi.latitude, poi.longitude)
                                        icon = originalDrawable
                                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                        title = poi.name
                                        snippet = poi.description
                                    },
                                )
                            }
                        }
                }
            },
            update = { view ->
                view.controller.setCenter(geoPoint)
            }
        )
    }
}

@Composable
fun MapAllScene(L: LiveData<List<Location>>, geoPoint: GeoPoint, vm : ActionsViewModel) {
    val location: State<List<Location>?> = L.observeAsState()
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .clipToBounds()
            .background(Color(255, 240, 128)),
    ) {


        if (location.value != null) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK);
                        setMultiTouchControls(true)
                        controller.setCenter(geoPoint)
                        controller.setZoom(3.0)

                        overlays.add(
                            Marker(this).apply {
                                position = GeoPoint(vm.currentLocation.value!!.latitude, vm.currentLocation.value!!.longitude)
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                title = vm.user.value!!.email
                            },
                        )

                        if (location.value != null)
                            for (l in location.value!!) {
                                val imageName = vm.getCategoryIcon(l.category ?: "")
                                val int= getResourceIdForImage(imageName)
                                if (int != 0) {
                                    val bitmap = BitmapFactory.decodeResource(context.resources, int ?: 0)
                                    val width = 80
                                    val height = 80
                                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
                                    val marker = Marker(this).apply {
                                        position = GeoPoint(l.latitude, l.longitude)
                                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                        title = l.name
                                        snippet = l.description
                                        icon = BitmapDrawable(context.resources, scaledBitmap)
                                    }
                                    overlays.add(marker)
                                } else {
                                    val originalDrawable = ContextCompat.getDrawable(context, org.osmdroid.library.R.drawable.ic_menu_mylocation)
                                    val color = ContextCompat.getColor(context, R.color.red)
                                    originalDrawable?.let {
                                        val mutableDrawable = DrawableCompat.wrap(it.mutate())
                                        DrawableCompat.setTint(mutableDrawable, color)
                                    }
                                    overlays.add(
                                        Marker(this).apply {
                                            position = GeoPoint(l.latitude, l.longitude)
                                            icon = originalDrawable
                                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                            title = l.name
                                            snippet = l.description
                                        },
                                    )
                                }
                            }
                    }
                },
                update = { view ->
                    view.controller.setCenter(geoPoint)
                }
            )
        }
    }
}
private fun createCirclePoints(center: GeoPoint, radius: Double): List<GeoPoint> {
    val points = mutableListOf<GeoPoint>()
    val numPoints = 100
    val radiusInDegrees = radius / 111_000.0

    for (i in 0 until numPoints) {
        val theta = (2.0 * Math.PI * i.toDouble()) / numPoints.toDouble()
        val x = radiusInDegrees * Math.cos(theta)
        val y = radiusInDegrees * Math.sin(theta)

        val pointLatitude = center.latitude + x
        val pointLongitude = center.longitude + y

        points.add(GeoPoint(pointLatitude, pointLongitude))
    }
    return points
}