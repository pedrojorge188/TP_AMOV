package pt.isec.amov.ui.screens

import DeleteDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.Category
import pt.isec.amov.ui.composables.IconDropdown
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.NavigationData
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(navController: NavHostController, vm: ActionsViewModel,categoryLiveData : LiveData<List<Category>>,onSelected : (NavigationData) -> Unit ) {
    var addCategory by remember { mutableStateOf("") }
    var addCategoryDescription by remember { mutableStateOf("") }
    val iconList = listOf("cidade", "desporto", "ginasio", "montanhas", "restaurante", "museu","natureza","praia")
    var expanded by remember  { mutableStateOf(false) }
    var type by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
        //Row {
        OutlinedTextField(
            value = addCategory,
            onValueChange = {
                addCategory = it
            },
            label = { Text(stringResource(R.string.category_name)) },
            singleLine = true
        )
        OutlinedTextField(
            value = addCategoryDescription,
            onValueChange = {
                addCategoryDescription = it
            },
            label = { Text(stringResource(R.string.insert_description)) },
            singleLine = true
        )



        Button(
            onClick = { expanded = !expanded },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF02458A)
            ),
            modifier = Modifier
                .width(300.dp)
                .padding(12.dp)
                .height(60.dp)
                .border(2.dp,Color(0xFF02458A))
            , // Altura ajustada
            shape = MaterialTheme.shapes.large
        ) {
            if(type == ""){
                Text(text = stringResource(R.string.Tipo))
            }else{
                Text(text = getTranslation(type))
            }
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            iconList.forEach { icon ->
                DropdownMenuItem(
                    text = { Text(getTranslation(icon)) },
                    onClick = {
                        type= icon
                        expanded = false
                    }
                )


            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        NormalBtn(
            onClick = {
                if (addCategory.isNotBlank() && addCategoryDescription.isNotBlank() && type.isNotBlank()){
                    vm.addCategory(addCategory, addCategoryDescription,type)
                    addCategory = ""
                    addCategoryDescription= ""
                    type = ""
                }else{
                    vm.error.value = "You must enter all requirements!"
                }
            },
            text = stringResource(id = R.string.add_category)
        )

        val category: State<List<Category>?> = categoryLiveData.observeAsState()
        if (category.value!=null){
            LazyColumn (
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {

                items(category.value!!, key = { it.id } ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ){
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
                            IconButton(
                                onClick = {
                                    onSelected(NavigationData(it.id, Screens.CATEGORY_DETAILS))
                                          },
                            ) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "Info"
                                )
                            }

                            if (vm.user.value?.email == it.createdBy){
                                DeleteDialog(onClick = {
                                    vm.deleteCategory(it.name);
                                })
                            }




                        }
                    }
                }
            }
        }

    }
}
@Composable
private fun getTranslation(txt: String): String {
    // Mapeia o nome da imagem para o ID do recurso
    return when (txt) {
        "cidade" -> stringResource(R.string.cidade)
        "desporto" -> stringResource(R.string.desporto)
        "ginasio" -> stringResource(R.string.ginasio)
        "montanhas" -> stringResource(R.string.montanhas)
        "restaurante" -> stringResource(R.string.restaurante)
        "museu" -> stringResource(R.string.museu)
        "natureza" -> stringResource(R.string.natureza)
        "praia" -> stringResource(R.string.praia)
        else -> "erro"
    }
}