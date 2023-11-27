package pt.isec.amov.ui.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pt.isec.amov.utils.camera.Utils

@Composable
fun SelectGalleryImg(imagePath: MutableState<String?>) {

    val context = LocalContext.current
    val galleryImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) {
            imagePath.value = null
            return@rememberLauncherForActivityResult
        }
        imagePath.value = Utils.createFileFromUri(context, uri)
    }
    Button(
        onClick = {
            galleryImage.launch(PickVisualMediaRequest())
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
            Icons.Filled.List,
            contentDescription = "getPhoto",
            tint = Color.White
        )
    }
}