
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.R

@Composable
fun RedWarningIconButton(
    onClick: () -> Unit,
    itemInfo: String,
    progressValue: Float
) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(
        onClick = { openDialog.value = true },
    ) {
        Icon(
            Icons.Filled.Warning,
            contentDescription = "danger",
            tint = Color.Red
        )
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = stringResource(R.string.warning_info_title)) },
            text = {
                Column {
                    Text(text = stringResource(R.string.info_modal_text, itemInfo))
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(progress = progressValue.coerceIn(0f, 2f) / 2f) // Normaliza o progresso para escala de 0 a 1
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onClick()
                    }
                ) {
                    Text(text = stringResource(R.string.vote))
                }
            }
        )
    }
}


@Composable
fun DeleteDialog(
    onClick: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(
        onClick = { openDialog.value = true },
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "danger",
            tint = Color.Black
        )
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = stringResource(R.string.delete)) },
            text = {
                Column {
                    Text(text = stringResource(R.string.certain))
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onClick()
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            }
        )
    }
}