package top.nekohelper.wallpaperhelper.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import top.nekohelper.wallpaperhelper.WallpaperApp
import java.io.File

private const val TAG = "UriExt"
suspend fun Uri.copyTo(toFile: File): File? = withContext(Dispatchers.IO + NonCancellable) {
    val app = WallpaperApp.appContext

    // copy uri to file
    Log.d(TAG, "toFile path is ${toFile.absolutePath} ,start copy...")
    kotlin.runCatching {
        app.contentResolver.openInputStream(this@copyTo).use { input ->
            toFile.outputStream().use { output ->
                input!!.copyTo(output)
            }
        }
    }.onFailure { e ->
        Log.e(TAG,"Copy file from uri failed")
        e.printStackTrace()
        // stop if fail
        return@withContext null
    }.onSuccess {
        Log.d(TAG, "Copy file from uri success")
    }

    toFile
}

suspend fun Uri.getMimeType(app: Context): String? = withContext(Dispatchers.IO + NonCancellable) {
    val extension: String? = if (scheme == ContentResolver.SCHEME_CONTENT) {
        val mime = MimeTypeMap.getSingleton()
        mime.getExtensionFromMimeType(app.contentResolver.getType(this@getMimeType))
    } else {
        val innerPath = path ?: return@withContext null
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(innerPath)).toString())
    }
    return@withContext extension
}