package pt.isec.amov.ui.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.Category
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.composables.getResourceIdForImage
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun EditCategoryScreen(
    navController: NavHostController,
    vm: ActionsViewModel,
    category: Category
) {
    var updatedCategory = remember { mutableStateOf(category.copy()) }
    var addCategory by remember { mutableStateOf(category.name) }
    var addCategoryDescription by remember { mutableStateOf(category.description) }
    val isDropdownOpen = remember { mutableStateOf(false) }
    val iconList = listOf(
        "cidade",
        "desporto",
        "ginasio",
        "montanhas",
        "restaurante",
        "museu",
        "natureza",
        "praia"
    )
    var expanded by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf(category.iconUrl) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (vm.error.value != null) {
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
                .border(2.dp, Color(0xFF02458A)), // Altura ajustada
            shape = MaterialTheme.shapes.large
        ) {
            if (type == "") {
                Text(text = stringResource(R.string.Tipo))
            } else {
                Text(text = getTranslation(type!!))
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
                val int = getResourceIdForImage(icon)
                DropdownMenuItem(
                    text = {
                        Row {
                            if (int != null) {
                                Image(

                                    painter = painterResource(id = int),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(10.dp)
                                        .height(10.dp)

                                )
                                Spacer(modifier = Modifier.width(15.dp))
                            }
                            Text(getTranslation(icon))
                        }
                    },
                    onClick = {
                        type = icon
                        expanded = false
                    }
                )


            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        NormalBtn(
            onClick = {
                if (addCategory.isNotBlank() && addCategoryDescription.isNotBlank()) {

                    updatedCategory.value.name = addCategory
                    updatedCategory.value.description = addCategoryDescription
                    updatedCategory.value.iconUrl = type

                    vm.updateCategory(updatedCategory.value)
                    navController.popBackStack()

                } else {
                    vm.error.value = "You must enter all requirements!"
                }
            },
            text = stringResource(id = R.string.save)
        )
    }
}