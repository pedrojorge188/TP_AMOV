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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.ui.composables.SearchBar
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalInterestListScreen(NavHostController: NavHostController,
                            locals : List<String>, /*Vai ter de deixar de ser string*/
                            onSelected : (String) -> Unit /*onSelected : (Int) -> Unit*/ )  //para mudar
{
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Screens.LOCATION)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            items(locals, key = { it } /*key = it.id*/) { //tem de mudar

                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        onSelected(it /*key = it.id*/)
                        NavHostController.navigate(Screens.LOCAL.route)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 20.dp)
                        )
                        Row {

                            //Isto só aparece se for uma informação sem as votacoes necessarias (mudar quando implementar firebase)
                            RedWarningIconButton(
                                onClick = {
                                    //ação para colocar mais uma nota
                                },
                                itemInfo = it,
                                1F // este valor vai ser recevido dentro do item mais tarde e corresponde ao numero de votaçoes
                            )

                            IconButton(
                                onClick = {
                                    onSelected(it /*key = it.id*/) //tem que mudar

                                },
                            ) {
                                Icon(
                                    Icons.Filled.MoreVert,
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

