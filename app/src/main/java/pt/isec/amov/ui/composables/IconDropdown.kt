package pt.isec.amov.ui.composables

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.R
import pt.isec.amov.ui.viewmodels.Screens

@Composable
fun IconDropdown() {
    val iconList = listOf("cidade", "desporto", "ginasio", "montanhas", "restaurante", "museu","natureza","praia")
    var expanded by remember  { mutableStateOf(false) }
    var type by remember { mutableStateOf("") }


    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        iconList.forEach { icon ->
            DropdownMenuItem(
                text = { Text(icon) },
                onClick = {
                    type= icon
                    expanded = false
                }
            )


        }
    }
}