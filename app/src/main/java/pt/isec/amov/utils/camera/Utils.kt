package pt.isec.amov.utils.camera

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class Utils {
    companion object {
        fun getTempFilename(context: Context) : String =
            File.createTempFile(
                "image", ".img",
                context.externalCacheDir
            ).absolutePath

        fun createFileFromUri(
            context: Context,
            uri : Uri,
            filename : String = getTempFilename(context)
        ) : String {
            FileOutputStream(filename).use { outputStream ->
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return filename
        }

    }
}