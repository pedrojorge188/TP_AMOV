package pt.isec.amov.ui.screens.lists

import RedWarningIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
                              locals : List<PointOfInterest>,
                              onSelected : (NavigationData) -> Unit)
{
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.POINT_OF_INTEREST, vm)
        Spacer(modifier = Modifier.height(16.dp))

        if(locals.isEmpty())
            NormalBtn(onClick = { NavHostController.navigate(Screens.ADD_POI.route) }, text = stringResource(id = R.string.add_location))

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            items(locals, key = { it.id }) {
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
                                .padding(horizontal = 20.dp, vertical = 20.dp)
                        )
                        Row {

                            if(it.votes < 2){
                                RedWarningIconButton(
                                    onClick = {
                                        //ação para colocar mais uma nota
                                    },
                                    itemInfo = it.name,
                                    it.votes.toFloat()
                                )
                            }

                            IconButton(
                                onClick = {
                                    onSelected(NavigationData(it.id, Screens.POINT_OF_INTEREST_DETAILS))
                                },
                            ) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "Info"
                                )
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(20F)
                            .fillMaxHeight(20F)
                    ) {
                        if (it.photoUrl != "") {
                            AsyncImage(model = it.photoUrl, contentDescription = "")
                        }

                    }
                }
            }
        }
    }
}

