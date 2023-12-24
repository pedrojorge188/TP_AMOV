package pt.isec.amov.ui.screens.locations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.osmdroid.util.GeoPoint
import pt.isec.amov.models.Location
import pt.isec.amov.ui.composables.MapScene
import pt.isec.amov.ui.viewmodels.ActionsViewModel
@Composable
fun LocationMapScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    location: Location
) {
    val geoPoint by remember { mutableStateOf(
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
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MapScene(POI = viewModel.getPointOfInterestList().value , geoPoint = geoPoint, true, viewModel)
        }
    }
}

