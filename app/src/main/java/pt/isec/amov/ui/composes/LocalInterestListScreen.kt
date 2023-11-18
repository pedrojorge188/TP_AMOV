package pt.isec.amov.ui.composes

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
import androidx.compose.material3.Divider
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

@Composable
fun LocalInterestListScreen(NavHostController: NavHostController, title: MutableState<String>) {

    title.value = stringResource(id = R.string.interests_locations)
    Column {

        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.LOCAL)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            //Array so para testes
            items(
                listOf(
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3",
                    "Local 1",
                    "Local 2",
                    "Local 3"
                )
            ) { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            /*Determinar o click*/

                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )

                    IconButton(
                        onClick = { NavHostController.navigate(Screens.MAP.route) }, //mudar mais tarde
                    ) {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = "Location"
                        )
                    }
                }
                Divider(color = Color.Gray)
            }
        }
    }
}