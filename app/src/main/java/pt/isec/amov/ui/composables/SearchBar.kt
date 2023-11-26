package pt.isec.amov.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.R
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(Screen: Screens, vm: ActionsViewModel) {

    var expandedOrderBy by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var orderBy by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(
                color = Color.Gray.copy(alpha = 0.2f)
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {

            Row {
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text(stringResource(R.string.search)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray.copy(alpha = 0.2f))
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row {

                Button(
                    onClick = { expandedOrderBy = !expandedOrderBy },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF02458A),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    if(orderBy == ""){
                        Text(text = stringResource(R.string.order))
                    }else{
                        Text(text = orderBy)
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(
                    expanded = expandedOrderBy,
                    onDismissRequest = { expandedOrderBy = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.order_name)) },
                        onClick = {
                            orderBy = "Ord.Alfabética"
                            expandedOrderBy = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.order_distance)) },
                        onClick = {
                            orderBy = "Distância"
                            expandedOrderBy = false
                        }
                    )
                }

                if(Screen == Screens.LOCAL) {
                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = { expandedCategory = !expandedCategory },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF02458A),
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.extraSmall
                    ) {

                        Text(text = stringResource(R.string.category))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "more category"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {

                        vm.getCategorys().forEach() {
                            DropdownMenuItem(
                                text = { it.name },
                                onClick = {
                                    selectedCategory = it.name
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }
            }

            //mais tarde adicionar categoria
            if(orderBy != "" || search != "" || selectedCategory != ""){
                Button(
                    onClick = {
                              orderBy = "";
                              search  = "";
                              selectedCategory = "";
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF02458A),
                        contentColor = Color.White
                    ),
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }

        }
    }
}