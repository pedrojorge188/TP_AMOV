
package pt.isec.amov.ui.screens.locations

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import pt.isec.amov.R
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.ui.composables.LatitudeLongitudeDialog
import pt.isec.amov.ui.composables.LocationSelectionButtons
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.composables.SelectGalleryImg
import pt.isec.amov.ui.composables.TakePhoto
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.utils.firebase.StoreUtil

@Composable
fun EditLocationScreen(navController: NavHostController, vm: ActionsViewModel, location: Location) {

    var locationName by remember { mutableStateOf(location.name) }
    var locationDescription by remember { mutableStateOf(location.description) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(location.category) }
    var showLatLonDialog by remember { mutableStateOf(false) }
    var latitude by remember { mutableStateOf(location.latitude) }
    var longitude by remember { mutableStateOf(location.longitude) }
    var updatedLocation = remember { mutableStateOf(location.copy()) }
    var locationOrigin by remember { mutableStateOf("")}
    var photoReplace by remember { mutableStateOf(false)}
    val categories: State<List<Category>?> = vm.getCategorys().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(vm.error.value != null) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "danger",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Error: ${vm.error.value}", color = Color.Red)
            }
        }

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

        Spacer(modifier = Modifier.height(16.dp))
        Column {
            NormalBtn(
                onClick = {
                    expanded = !expanded
                },
                text = if (selectedCategory != null) {
                    selectedCategory!!
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
                categories.value?.forEach() { category ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategory = category.name

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

            if (latitude.toString().isNotBlank() && longitude.toString().isNotBlank()) {
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
                    vm.currentLocation.value?.let { location ->
                        latitude = location.latitude
                        longitude = location.longitude
                        locationOrigin = "Localização do meu dispositivo"
                        showLatLonDialog = false
                    }
                }
            )
            if (showLatLonDialog) {
                LatitudeLongitudeDialog(
                    latitude = latitude.toString(),
                    onLatitudeChange = { latitude = it.toDouble() },
                    longitude = longitude.toString(),
                    onLongitudeChange = { longitude = it.toDouble() },
                    onDismiss = { showLatLonDialog = false }
                )
            }

        }

        Text(
            text = stringResource(R.string.current_photo),
            textAlign = TextAlign.Center)

        if (location.photoUrl != "") {
            val storage = Firebase.storage
            val storageRef: StorageReference? = if (location.photoUrl!!.isNotBlank()) {
                storage.reference.child(location.photoUrl!!)
            } else {
                null
            }
            val imageUrl = remember { mutableStateOf<String?>(null) }

            LaunchedEffect(key1 = storageRef) {
                if (storageRef != null) {
                    try {
                        val downloadUrl = storageRef.downloadUrl.await().toString()
                        imageUrl.value = downloadUrl
                    } catch (e: Exception) {
                        imageUrl.value = null
                    }
                }
            }

            if (imageUrl.value != null) {
                AsyncImage(
                    model = imageUrl.value!!,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.imagem_n_o_dispon_vel),
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator()
                }
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
                if(vm.imagePath.value != null){
                    photoReplace = true;
                    AsyncImage(vm.imagePath.value , contentDescription = "")
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        NormalBtn(
            onClick =
            {
                if (locationName.isNotBlank() && locationDescription.isNotBlank()
                ) {

                    updatedLocation.value.name = locationName
                    if(vm.imagePath.value != null) {
                        updatedLocation.value.photoUrl = "images/"+vm.imagePath.value
                        StoreUtil.deleteImagesFromStorage(location.photoUrl!!)
                    }
                    updatedLocation.value.description = locationDescription
                    updatedLocation.value.category = selectedCategory
                    updatedLocation.value.latitude = latitude
                    updatedLocation.value.longitude = longitude
                    updatedLocation.value.votedBy = emptyList()
                    vm.updateLocation(updatedLocation.value)
                    navController.popBackStack()

                } else {
                    vm.error.value = "You must enter all requirements!"
                }
            },
            text = stringResource(R.string.save)
        )
    }
}