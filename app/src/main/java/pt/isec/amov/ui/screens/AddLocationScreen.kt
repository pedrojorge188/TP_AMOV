
package pt.isec.amov.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pt.isec.amov.R
import pt.isec.amov.models.Category
import pt.isec.amov.ui.composables.LatitudeLongitudeDialog
import pt.isec.amov.ui.composables.LocationSelectionButtons
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.composables.SelectGalleryImg
import pt.isec.amov.ui.composables.TakePhoto
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.utils.location.CurrentLocation

@Composable
fun AddLocationScreen(navController: NavHostController, vm: ActionsViewModel) {

        var locationName by remember { mutableStateOf("") }
        var locationDescription by remember { mutableStateOf("") }
        var authorsName by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var showLatLonDialog by remember { mutableStateOf(false) }
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }
        var locationOrigin by remember { mutableStateOf("")}
        val applicationContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = locationName,
            onValueChange = {
                locationName = it
            },
            label = { Text(stringResource(R.string.location_name)) },
            singleLine = true
        )

        OutlinedTextField(
            value = locationDescription,
            onValueChange = {
                locationDescription = it
            },
            label = { Text(stringResource(R.string.insert_description)) },
            singleLine = false
        )
        OutlinedTextField(
            value = authorsName,
            onValueChange = {
                authorsName = it
            },
            label = { Text(stringResource(R.string.authors_name_qa)) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column {
            NormalBtn(
                onClick = {
                    expanded = !expanded
                },
                text = if (selectedCategory != null) {
                    selectedCategory!!.name
                } else {
                    stringResource(R.string.choose_category)
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                vm.getCategorys().forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        },
                        text = {
                            Text(
                                text = category.name,
                                style = LocalTextStyle.current.copy(color = Color.Black)
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp), text = stringResource(R.string.coordenadas_da_localiza_o)
            )

            if(latitude.isNotBlank() && longitude.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(3.dp),
                    text = "$locationOrigin\nlat:$latitude    long:$longitude",
                    color = if (locationOrigin.isNotBlank()) {
                        Color.Red
                    } else {
                        Color.Black
                    }
                )
            }

            LocationSelectionButtons(
                onInputButtonClick = {
                    showLatLonDialog = true
                    locationOrigin = ""
                },
                onMapButtonClick = {
                        CurrentLocation.getCurrentLocation(applicationContext) { lat, lon ->
                            latitude = lat
                            longitude = lon
                        }
                        locationOrigin = "Localização do seu dispositivo"
                }
            )
            if (showLatLonDialog) {
                LatitudeLongitudeDialog(
                    latitude = latitude,
                    onLatitudeChange = { latitude = it },
                    longitude = longitude,
                    onLongitudeChange = { longitude = it },
                    onDismiss = { showLatLonDialog = false }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp), text = stringResource(R.string.location_photo)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TakePhoto(imagePath = vm.imagePath)
                Spacer(modifier = Modifier.width(2.dp))
                SelectGalleryImg(imagePath = vm.imagePath)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(50F)
                    .fillMaxHeight(50F)
            ) {
                if (vm.imagePath.value != null) {
                    AsyncImage(model = vm.imagePath.value, contentDescription = "")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        NormalBtn(
            onClick =
            {
                if (locationName.isNotBlank() && locationDescription.isNotBlank()
                    && authorsName.isNotBlank() && selectedCategory != null
                    && latitude.isNotBlank() && longitude.isNotBlank()
                ) {

                    vm.addLocation(
                        locationName, locationDescription, authorsName,
                        selectedCategory!!, latitude.toDouble(), longitude.toDouble()
                    )

                    navController.popBackStack()

                } else {
                    //Toast.makeText(LocalContext.current, "Ocorreu um erro", Toast.LENGTH_SHORT).show()
                }
            },
            text = stringResource(id = R.string.add_location)
        )
    }

}