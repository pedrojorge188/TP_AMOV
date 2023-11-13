package pt.isec.amov.composes

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.composes.items.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(NavHostController: NavHostController) {

    Column {

        Text(
            text = stringResource(R.string.location_list),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(bottom = 10.dp, top = 10.dp)
                .background(MaterialTheme.colorScheme.background)
                .align(alignment = Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            //Array so para testes
            items(listOf("Item 1", "Item 2", "Item 3","Item 1", "Item 2", "Item 3","Item 1", "Item 2",
                "Item 3","Item 1", "Item 2", "Item 3","Item 1", "Item 2", "Item 3",
                "Item 1", "Item 2", "Item 3","Item 1", "Item 2", "Item 3")) { item ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp).clickable{
                        NavHostController.navigate(Screens.LOCAL.route) //mudar mais tarde (vai ter de redirecionar para os locais de intresse desejados)
                    },
                    horizontalArrangement = Arrangement.SpaceBetween){
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
