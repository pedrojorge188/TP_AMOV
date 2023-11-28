package pt.isec.amov.ui.screens.maps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import pt.isec.amov.R
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun PointOfInterestMapScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    item: PointOfInterest
) {
    val lalng = LatLng(item.latitude, item.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lalng, 13f)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.local_de_interesse) + item.name,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
                .weight(1f)
        ) {
            /*
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = lalng),
                    title = item.name,
                    snippet = item.name,
                )
            }
             */
        }
    }
}