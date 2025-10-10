package com.vcompass.core.compose_view

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vcompass.core.R
import java.io.File

sealed class AvatarModel {
    data class Url(val url: String) : AvatarModel()
    data class Resource(@param:DrawableRes val resId: Int) : AvatarModel()
    data class UriAvatar(val uri: Uri) : AvatarModel()
    data class FileAvatar(val file: File) : AvatarModel()
    object None : AvatarModel()
}

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    avatar: AvatarModel,
) {
    val model = when (avatar) {
        is AvatarModel.Url -> avatar.url
        is AvatarModel.Resource -> avatar.resId
        is AvatarModel.UriAvatar -> avatar.uri
        is AvatarModel.FileAvatar -> avatar.file
        AvatarModel.None -> null
    }

    AsyncImage(
        model = model,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.clip(CircleShape),
    )
}

@Preview(showSystemUi = true)
@Composable
fun UserAvatarPreview() {
    Column(){
        UserAvatar(
            modifier = Modifier.size(48.dp),
            avatar = AvatarModel.Url("https://example.com/avatar.jpg")
        )
    }
}
