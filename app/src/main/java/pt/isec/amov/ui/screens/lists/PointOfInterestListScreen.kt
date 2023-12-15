package pt.isec.amov.ui.screens.lists

import RedWarningIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.composables.SearchBar
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.NavigationData
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointOfInterestListScreen(NavHostController: NavHostController,
                              vm: ActionsViewModel,
                              localsLiveData : LiveData<List<PointOfInterest>>,
                              onSelected : (NavigationData) -> Unit)
{

    val locals: State<List<PointOfInterest>?> = localsLiveData.observeAsState()

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.POINT_OF_INTEREST, vm)

        if(locals.value!!.isEmpty())
            NormalBtn(onClick = { NavHostController.navigate(Screens.ADD_POI.route) }, text = stringResource(id = R.string.add_interest_location))

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            items(locals.value!!, key = { it.id }) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        onSelected(NavigationData(it.id, Screens.POINT_OF_INTEREST_DETAILS))
                    }
                ) {
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

                            if(it.votes < 2){
                                RedWarningIconButton(
                                    onClick = {
                                        //ação para colocar mais uma nota
                                    },
                                    itemInfo = it.name,
                                    it.votes.toFloat()
                                )
                            }
                            if(vm.user.value != null)
                                if(it.createdBy.equals( vm.user.value!!.email )) {
                                    IconButton(
                                        onClick = {
                                            vm.deletePOI(it.name);
                                        },
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Info"
                                        )
                                    }
                                }

                    }
                }
            }
        }
    }
}

