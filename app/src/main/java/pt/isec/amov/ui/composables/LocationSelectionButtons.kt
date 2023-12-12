package pt.isec.amov.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pt.isec.amov.R

@Composable
fun LocationSelectionButtons(
    onInputButtonClick: () -> Unit,
    onMapButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                onInputButtonClick.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF02458A),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(10.dp)
                .height(52.dp)
        ) {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "addCoords",
                tint = Color.White
            )
        }
        Button(
            onClick = {
                onMapButtonClick.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF02458A),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(10.dp)
                .height(52.dp)
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "addCoords",
                tint = Color.White
            )
        }
    }
}

@Composable
fun LatitudeLongitudeDialog(
    latitude: String,
    onLatitudeChange: (String) -> Unit,
    longitude: String,
    onLongitudeChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Inserir Latitude e Longitude") },
        text = {
            Column {
                TextField(
                    value = latitude,
                    onValueChange = { onLatitudeChange(it) },
                    label = { Text(stringResource(R.string.latitude)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { /* Define alguma ação quando 'Next' é pressionado */ }
                    )
                )
                TextField(
                    value = longitude,
                    onValueChange = { onLongitudeChange(it) },
                    label = { Text(stringResource(R.string.longitude)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
