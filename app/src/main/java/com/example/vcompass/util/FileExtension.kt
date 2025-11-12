package com.example.vcompass.util

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "IMG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}
fun Context.getUriForFile(file: File): Uri {
    return  FileProvider.getUriForFile(
        this,
        "${packageName}.fileprovider",
        file
    )
}
fun Map<Uri, Int>.addImage(uri: Uri): Map<Uri, Int> {
    if (this.containsKey(uri)) return this
    val newMap = this.toMutableMap()
    newMap[uri] = newMap.size + 1
    return newMap
}
fun Map<Uri, Int>.addImages(uris: List<Uri>): Map<Uri, Int> {
    val newMap = this.toMutableMap()
    for (uri in uris) {
        if (!newMap.containsKey(uri)) {
            newMap[uri] = newMap.size + 1
        }
    }
    return newMap
}
fun Map<Uri, Int>.toggleItem(uri: Uri, maxItems: Int): Map<Uri, Int> {
    val newMap = this.toMutableMap()
    if (newMap.containsKey(uri)) {
        newMap.remove(uri)
    } else if (newMap.size < maxItems) {
        newMap[uri] = Int.MAX_VALUE
    }
    return newMap.entries
        .sortedBy { it.value }
        .mapIndexed { index, entry -> entry.key to (index + 1) }
        .toMap()
}

suspend fun loadGalleryImages(context: Context, maxCount: Int): List<Uri> {
    return withContext(Dispatchers.IO) {
        val images = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext() && images.size < maxCount) {
                val id = cursor.getLong(idColumn)
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(uri)
            }
        }
        images
    }
}
fun Context.launchCrop(inputUri: Uri, cropLauncher: ActivityResultLauncher<Intent>) {
    val destinationUri = Uri.fromFile(
        File(cacheDir, "cropped_${System.currentTimeMillis()}.jpg")
    )
    val options = UCrop.Options().apply {
        setHideBottomControls(true)
        setToolbarTitle("")
    }
    val uCrop = UCrop.of(inputUri, destinationUri)
        .withOptions(options)
        .withAspectRatio(1f, 1f)
        .withMaxResultSize(1080, 1080)

    cropLauncher.launch(uCrop.getIntent(this))
}

fun Context.isVideoUri(uri: Uri): Boolean {
    val contentResolver = this.contentResolver
    val type = contentResolver.getType(uri)
    return type?.startsWith("video") == true
}

fun Context.getVideoThumbnail(uri: Uri): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this, uri)
        val bitmap = retriever.getFrameAtTime(0)
        retriever.release()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}