
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isec.amov.R
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun ReportWarning(
    onClick: () -> Unit,
    itemName: String,
    locationId: String,
    poiName: String,
    itemReportedBy: List<String>? = null,
    userEmail: String,
    progressValue: Float,
    vm: ActionsViewModel
) {
    var showMessage = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (userEmail.isNotBlank() && itemReportedBy != null && userEmail in itemReportedBy) {
                showMessage.value = true
            } else {
                openDialog.value = true
            }
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
        Row {
            Icon(
                Icons.Filled.Build,
                contentDescription = "LocationOn",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = stringResource(R.string.report),
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = stringResource(R.string.report_point_of_interest) )},
            text = {
                Column {
                    Text(text = stringResource(R.string.do_you_want_to_submit_your_report))
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        vm.addReport(locationId, userEmail, poiName)
                        onClick()
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            }
        )
    }
    if (showMessage.value) {
        AlertDialog(
            onDismissRequest = { showMessage.value = false },
            title = { Text(text = stringResource(R.string.report_point_of_interest)) },
            text = {
                Column {
                    Text(text = stringResource(R.string.you_already_vote_on_this_item))
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showMessage.value = false
                        onClick()
                    }
                ) {
                    Text(text = stringResource(R.string.Back))
                }
            }
        )
    }
}
