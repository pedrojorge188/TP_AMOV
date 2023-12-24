package pt.isec.amov.ui.screens.locations

import EditAndDeleteDialog
import RedWarningIconButton
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.Location
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.composables.SearchBar
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.NavigationData
import pt.isec.amov.ui.viewmodels.Screens
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(NavHostController: NavHostController,
                       vm: ActionsViewModel,
                       locationLiveData : LiveData<List<Location>>,
                       onSelected : (NavigationData) -> Unit )
{
    var orderBy by remember { mutableStateOf("") }
    var searchBy by remember { mutableStateOf("") }
    var categoryBy by remember { mutableStateOf("") }
    val isDropdownOpen = remember { mutableStateOf(false) }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.LOCATION, vm){ s: String, s1: String, s2: String ->
            Log.d("ORDERBY", "Value: $s")
            Log.d("SEARCHBY", "Value: $s1")
            Log.d("CATEGORYBY", "Value: $s2")
            orderBy = s
            searchBy = s1
            categoryBy = s2


        }

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

        val location: State<List<Location>?> = locationLiveData.observeAsState()

        if(location.value != null) {

            if(location.value!!.isEmpty()){
                NormalBtn(onClick = { NavHostController.navigate(Screens.ADD_LOCATION.route) }, text = stringResource(id = R.string.add_location))
            }

            LazyColumn (
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                val filteredLocations = location.value!!.let {
                    if (searchBy.isNotEmpty()) {
                        Log.d("SEARCHBY", "Value: $searchBy")
                        it.filter { location ->
                            location.name.contains(searchBy, ignoreCase = true)/*&&location.category==*/ }
                    } else {
                        Log.d("SEARCHBY", "Val: $searchBy")

                        it
                    }
                }
                items(filteredLocations.sortedWith(compareBy { it.whatOrder(orderBy, vm) }), key = { it.id } ) {
                Card(
                        elevation = CardDefaults.cardElevation(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            onSelected(NavigationData(it.id, Screens.POINT_OF_INTEREST))
                        }
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 20.dp) ,
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.weight(1f,true))
                                if(vm.user.value != null){
                                if(it.votes < 2){
                                    RedWarningIconButton(
                                        onClick = {

                                        },
                                        itemName = it.name,
                                        locationId = it.id,
                                        itemVotedBy = it.votedBy,
                                        poiName = "",
                                        userEmail = vm.user.value!!.email,
                                        progressValue = it.votes.toFloat(),
                                        vm = vm
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        onSelected(NavigationData(it.id, Screens.LOCATION_DETAILS))
                                    },
                                ) {
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "Info"
                                    )
                                }


                                    if(it.createdBy.equals( vm.user.value!!.email )){
                                        Column {
                                            IconButton(
                                                onClick = {
                                                    isDropdownOpen.value = !isDropdownOpen.value
                                                },
                                            ) {
                                                Icon(
                                                    Icons.Filled.Settings,
                                                    contentDescription = "Info"
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            if (isDropdownOpen.value) {
                                                EditAndDeleteDialog(
                                                    onClickDelete = {
                                                        vm.deleteLocation(it.id);
                                                    },
                                                    onClickEdit = {
                                                        vm.locationId.value = it.id
                                                        NavHostController.navigate(Screens.EDIT_LOCATION.route)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }


                            }
                            Divider(
                                color = Color.Black,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 40.dp,
                                        end = 40.dp,
                                        bottom = 20.dp,
                                        top = 20.dp
                                    ),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = it.likes.toString(),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.like),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                                    Spacer(modifier = Modifier.weight(1f,true))
                                Text(
                                    text = it.dislikes.toString(),
                                    modifier = Modifier.padding(end = 8.dp)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.dislike),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )

                            }
                        }

                    }
                }
            }

        }
        FloatingActionButton(
            onClick = {
                NavHostController.navigate(Screens.MAP_OVERVIEW.route)
            },
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.End),
            containerColor  = Color(0xFF02458A) // Definindo a cor do botão flutuante
        ) {
            Icon(
                painter = painterResource(id = org.osmdroid.library.R.drawable.ic_menu_mapmode),
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
fun Location.whatOrder(orderBy: String, vm: ActionsViewModel): Comparable<*> {
    Log.d("ORDERBY", "Value: $orderBy")

    if (orderBy=="") return this.name.lowercase().reversed()
    else if (orderBy=="Ord.Alfabética") return this.name.lowercase()
    else if (orderBy=="Distância"){
        var currentlatitude=0.0
        var currentlongitude=0.0
        vm.currentLocation.value?.let { location ->
             currentlatitude = location.latitude
             currentlongitude = location.longitude
        }
        val latitudeDifference = (this.latitude - currentlatitude).absoluteValue
        val longitudeDifference = (this.longitude - currentlongitude).absoluteValue
        return Math.sqrt(latitudeDifference * latitudeDifference + longitudeDifference * longitudeDifference)
    }
    else    return this.name.lowercase().reversed()
}

