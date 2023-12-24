package pt.isec.amov.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import org.osmdroid.util.GeoPoint
import pt.isec.amov.R
import pt.isec.amov.models.Location
import pt.isec.amov.ui.composables.MapAllScene
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun AllMapOverview(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    L: LiveData<List<Location>>,
){
    val geoPoint by remember { mutableStateOf(
        GeoPoint(
            0.0, -8.69
        )
    ) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.all_locations),
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
            MapAllScene(L = L , geoPoint = geoPoint, viewModel)
        }
    }
}