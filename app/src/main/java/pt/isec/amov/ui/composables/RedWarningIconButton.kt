
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.R
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun RedWarningIconButton(
    onClick: () -> Unit,
    itemName: String,
    locationId: String,
    poiName: String,
    itemVotedBy: List<String>? = null,
    userEmail: String,
    progressValue: Float,
    vm: ActionsViewModel
) {
    var showMessage by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    IconButton(
        {
            if (userEmail.isNotBlank() && itemVotedBy != null && userEmail in itemVotedBy) {
                showMessage = true
            } else {
                openDialog.value = true
            }
        }
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
                    Text(text = stringResource(R.string.info_modal_text, itemName))
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(progress = progressValue.coerceIn(0f, 2f) / 2f)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        // Incrementar os votos
                        vm.addVote(locationId, userEmail, poiName)
                        onClick()
                    }
                ) {
                    Text(text = stringResource(R.string.vote))
                }
            }
        )
    }
    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            title = { Text(text = stringResource(R.string.warning_info_title)) },
            text = {
                Column {
                    Text(text = "Já votou nesta localização")
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(progress = progressValue.coerceIn(0f, 2f) / 2f)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showMessage = false
                        onClick()
                    }
                ) {
                    Text(text = "Voltar para trás")
                }
            }
        )
    }
}


@Composable
fun EditAndDeleteDialog(

    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    Column {

        IconButton(
            onClick = { onClickEdit() },
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "danger",
                tint = Color.Black
            )
        }

        IconButton(
            onClick = { openDialog.value = true },
        ) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "danger",
                tint = Color.Black
            )
        }
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
                        onClickDelete()
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            }
        )
    }
}
