package pt.isec.amov.ui.composes

import RedWarningIconButton
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.composes.items.SearchBar
import pt.isec.amov.utils.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(NavHostController: NavHostController, title: MutableState<String>) {
    title.value = stringResource(id = R.string.location_list)
    Column {

        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.LOCATION)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            //Array so para testes
            items(listOf("Location 1", "Location 2", "Location 3","Location 1", "Location 2", "Location 3","Location 1", "Location 2",
                "Location 3","Location 1", "Location 2", "Location 3","Location 1", "Location 2", "Location 3",
                "Location 1", "Location 2", "Location 3","Location 1", "Location 2", "Location 3")) { item ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        NavHostController.navigate(Screens.LOCAL.route) //mudar mais tarde (vai ter de redirecionar para os locais de intresse desejados)
                    },
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                    Row{
                        RedWarningIconButton(
                            onClick = { },
                            itemInfo = item,
                            1F // este valor vai ser recevido dentro do item mais tarde e corresponde ao numero de votaçoes
                        )

                        IconButton(
                            onClick = { NavHostController.navigate(Screens.MAP.route) }, //mudar mais tarde
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = "Location"
                            )
                        }
                        IconButton(
                            onClick = {NavHostController.navigate(Screens.LOCATION_DETAILS.route)}, //mudar mais tarde
                        ) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "Info"
                            )
                        }
                    }

                }
                Divider(color = Color.Gray)
            }
        }
    }
}


