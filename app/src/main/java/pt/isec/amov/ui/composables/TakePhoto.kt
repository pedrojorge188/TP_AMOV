package pt.isec.amov.ui.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import pt.isec.amov.utils.camera.Utils
import java.io.File

@Composable
fun TakePhoto(imagePath: MutableState<String?>) {

    val context = LocalContext.current
    var tempFile by remember { mutableStateOf("") }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (!success) {
            imagePath.value = null
            return@rememberLauncherForActivityResult
        }
        imagePath.value = tempFile
    }
    Button(
        onClick = {
            tempFile = Utils.getTempFilename(context)
            var fileUri = FileProvider.getUriForFile(
                context, "pt.isec.amov.android.fileprovider",
                File(tempFile)
            )
            cameraLauncher.launch(fileUri)
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
            contentDescription = "addPhoto",
            tint = Color.White
        )
    }


}