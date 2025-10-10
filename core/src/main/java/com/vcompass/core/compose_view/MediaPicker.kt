//package com.vcompass.core.compose_view
//
//import android.Manifest.permission.CAMERA
//import android.Manifest.permission.READ_EXTERNAL_STORAGE
//import android.Manifest.permission.READ_MEDIA_IMAGES
//import android.app.Activity
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Build
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.produceState
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.vcompass.core.R
//import com.vcompass.core.utils.extension.addImage
//import com.vcompass.core.utils.extension.addImages
//import com.vcompass.core.utils.extension.createImageFile
//import com.vcompass.core.utils.extension.getUriForFile
//import com.vcompass.core.utils.extension.getVideoThumbnail
//import com.vcompass.core.utils.extension.isVideoUri
//import com.vcompass.core.utils.extension.launchCrop
//import com.vcompass.core.utils.extension.loadGalleryImages
//import com.vcompass.core.utils.extension.toggleItem
//import com.vcompass.core.permission.rememberPermissionHandler
//import com.yalantis.ucrop.UCrop
//import kotlinx.coroutines.launch
//import java.io.File
//
//@Composable
//fun MediaPicker(
//    modifier: Modifier = Modifier,
//    isPickMultiMedia: Boolean = false,
//    isPickVideoOnly: Boolean = false,
//    isPickImageOnly: Boolean = false,
//    maxItems: Int = 20,
//    onMediaPicked: ((List<Uri>) -> Unit)? = null,
//) {
//    val context = LocalContext.current
//    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
//    var selectedImages by remember { mutableStateOf<Map<Uri, Int>>(emptyMap()) }
//    val scope = rememberCoroutineScope()
//    val photoFile = remember { mutableStateOf<File?>(null) }
//
//    // Crop
//    val cropLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val resultUri = UCrop.getOutput(result.data!!)
//                resultUri?.let {
//                    onMediaPicked?.invoke(listOf(resultUri))
//                }
//            } else if (result.resultCode == UCrop.RESULT_ERROR) {
//                val cropError = UCrop.getError(result.data!!)
//                cropError?.printStackTrace()
//            }
//        }
//    // Camera
//    val cameraLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
//            if (success) {
//                photoFile.value?.let { file ->
//                    val uri = context.getUriForFile(file)
//                    selectedImages = selectedImages.addImage(uri)
//                    context.launchCrop(uri, cropLauncher)
//                }
//            }
//        }
//
//    // Permission
//    val imagePermissionHandler = rememberPermissionHandler(
//        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE
//    ) {
//        scope.launch { imageUris = loadGalleryImages(context, maxCount = 100) }
//    }
//    val cameraPermissionHandler = rememberPermissionHandler(permission = CAMERA) {
//        photoFile.value = context.createImageFile()
//        photoFile.value?.let { file -> cameraLauncher.launch(context.getUriForFile(file)) }
//    }
//    // Gallery
//    val mediaPickerLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
//            uri?.let {
//                selectedImages = selectedImages.addImage(uri)
//                if (isPickImageOnly) {
//                    context.launchCrop(uri, cropLauncher)
//                } else {
//                    onMediaPicked?.invoke(selectedImages.keys.toList())
//                }
//            }
//        }
//    val multipleMediaPickerLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems)) { uris ->
//            selectedImages = selectedImages.addImages(uris)
//            onMediaPicked?.invoke(selectedImages.keys.toList())
//        }
//
//    fun pickMedia() {
//        val mediaType = when {
//            isPickImageOnly -> ActivityResultContracts.PickVisualMedia.ImageOnly
//            isPickVideoOnly -> ActivityResultContracts.PickVisualMedia.VideoOnly
//            else -> ActivityResultContracts.PickVisualMedia.ImageAndVideo
//        }
//
//        if (isPickMultiMedia) {
//            multipleMediaPickerLauncher.launch(PickVisualMediaRequest(mediaType))
//        } else {
//            mediaPickerLauncher.launch(PickVisualMediaRequest(mediaType))
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        imagePermissionHandler()
//        if (isPickVideoOnly) pickMedia()
//    }
//
//    if (!isPickVideoOnly) {
//        Column(modifier = modifier.fillMaxSize().background(Color.White)) {
//            PickerHeader(
//                isPickMultiMedia = isPickMultiMedia,
//                hasSelection = selectedImages.isNotEmpty(),
//                onClear = { selectedImages = linkedMapOf() },
//                onDone = { onMediaPicked?.invoke(selectedImages.keys.toList()) },
//                onOpenGallery = { pickMedia() },
//                onCamera = { cameraPermissionHandler() }
//            )
//
//            if (imageUris.isNotEmpty()) {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(3),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(1.dp)
//                ) {
//                    items(imageUris, key = { uri -> uri }) { uri ->
//                        val isSelected =
//                            if (isPickMultiMedia) selectedImages.containsKey(uri) else null
//                        val order = if (isPickMultiMedia) selectedImages[uri] else null
//                        GalleryItem(
//                            uri = uri,
//                            isSelected = isSelected,
//                            order = order,
//                            onClick = {
//                                if (isPickMultiMedia) {
//                                    selectedImages = selectedImages.toggleItem(uri, maxItems)
//                                } else {
//                                    selectedImages = selectedImages.addImage(uri)
//                                    if (isPickImageOnly) context.launchCrop(uri, cropLauncher)
//                                    else onMediaPicked?.invoke(listOf(uri))
//                                }
//                            }
//                        )
//                    }
//                }
//            } else {
//                // TODO: Need to replace with loading compose
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
//                        Text(
//                            "Loading...",
//                            fontSize = 16.sp,
//                            modifier = Modifier.padding(top = 8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PickerHeader(
//    isPickMultiMedia: Boolean,
//    hasSelection: Boolean,
//    onClear: () -> Unit,
//    onDone: () -> Unit,
//    onOpenGallery: () -> Unit,
//    onCamera: () -> Unit
//){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(48.dp)
//            .background(Color.White)
//            .padding(horizontal = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    )
//    {
//        Text(
//            text = "Select Image",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.SemiBold,
//            modifier = Modifier
//                .weight(1f)
//                .padding(start = 8.dp)
//        )
//
//        if (isPickMultiMedia) {
//            IconButton(
//                onClick = { onClear() },
//                enabled = hasSelection
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_clear),
//                    contentDescription = "Clear"
//                )
//            }
//
//            IconButton(
//                onClick = { onDone() },
//                enabled = hasSelection
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_done_btn),
//                    contentDescription = "Done"
//                )
//            }
//        } else {
//            IconButton(onClick = { onOpenGallery() }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_gallery_btn),
//                    contentDescription = "Gallery"
//                )
//            }
//
//            IconButton(onClick = { onCamera() }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_camera_btn),
//                    contentDescription = "Camera"
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun GalleryItem(
//    uri: Uri,
//    isSelected: Boolean?,
//    order: Int?,
//    onClick: () -> Unit
//){
//    Box(
//        modifier = Modifier
//            .aspectRatio(1f)
//            .padding(1.dp)
//            .clickable { onClick() }
//    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(uri)
//                .crossfade(true)
//                .build(),
//            placeholder = painterResource(R.drawable.img_placeholder),
//            contentScale = ContentScale.Crop,
//            contentDescription = uri.toString(),
//            modifier = Modifier.fillMaxSize()
//        )
//        if (isSelected == true) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.4f))
//            )
//        }
//        order?.let {
//            Text(
//                text = "$order",
//                color = Color.White,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(4.dp)
//                    .background(
//                        Color.Black.copy(alpha = 0.5f),
//                        shape = CircleShape
//                    )
//                    .padding(horizontal = 6.dp, vertical = 2.dp)
//            )
//        }
//    }
//}
//
//// Temp Compose: Build to testing
//@Composable
//fun TempImageShow(
//    uri: Uri
//) {
//    val context = LocalContext.current
//    val isVideo = remember(uri) { context.isVideoUri(uri) }
//
//    val thumbnailBitmap by produceState<Bitmap?>(initialValue = null, uri) {
//        if (isVideo) {
//            value = context.getVideoThumbnail(uri)
//        }
//    }
//    Box(
//        modifier = Modifier
//            .aspectRatio(1f)
//            .clip(RoundedCornerShape(8.dp))
//            .background(Color.LightGray)
//    ) {
//        if (isVideo && thumbnailBitmap != null) {
//            Image(
//                bitmap = thumbnailBitmap!!.asImageBitmap(),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = "Play icon",
//                tint = Color.White,
//                modifier = Modifier
//                    .size(36.dp)
//                    .align(Alignment.Center)
//            )
//        } else {
//            AsyncImage(
//                model = uri,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//}
//
//
//@Preview(showSystemUi = true)
//@Composable
//fun MediaPickerPreview() {
//    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
//    Column {
//        if (selectedImages.isEmpty()) {
//            MediaPicker(
//                isPickImageOnly = true
//            ) {
//                selectedImages = it
//            }
//        } else {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(selectedImages) { uri ->
//                    TempImageShow(uri)
//                }
//            }
//        }
//    }
//}
//
