package pt.isec.amov.ui.screens.poi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.osmdroid.util.GeoPoint
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.composables.MapScene
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun PointOfInterestMapScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    item: PointOfInterest
) {

    val geoPoint by remember{ mutableStateOf(
        GeoPoint(
            item.latitude, item.longitude
        )
    ) }

    val lPOI = mutableListOf<PointOfInterest>()
    lPOI.add(item)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )

        MapScene(POI = lPOI, geoPoint = geoPoint, location = false, viewModel)

    }
}