package com.example.vcompass.screen.explore

import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.util.clickNoRipple
import com.example.vcompass.util.formatThousandK
import com.example.vcompass.util.scaleOnClick
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerSection(
    video: VideoItem,
    isActive: Boolean = true,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var position by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }
    var isPlaying by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val exoPlayer = if (LocalInspectionMode.current) {
        null
    } else {
        remember(video.id) {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(video.videoUrl)
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = isActive
                repeatMode = Player.REPEAT_MODE_ONE
            }
        }
    }

    LaunchedEffect(isActive) {
        exoPlayer?.playWhenReady = isActive
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> isLoading = true
                    Player.STATE_READY -> {
                        isLoading = false
                        duration = exoPlayer?.duration ?: 0L
                    }

                    else -> {}
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlayerError(error: PlaybackException) {
                isLoading = false
            }
        }
        exoPlayer?.addListener(listener)

        onDispose {
            exoPlayer?.removeListener(listener)
            exoPlayer?.release()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickNoRipple {
                exoPlayer?.let {
                    it.playWhenReady = !it.playWhenReady
                }
            }
    ) {
        if (exoPlayer != null) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        useController = false
                        setKeepContentOnPlayerReset(true)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        if (isLoading || exoPlayer == null) {
            CoreImage(
                source = CoreImageSource.Url(video.thumbnailUrl),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        if (!isPlaying) {
            CoreIcon(
                imageVector = Icons.Filled.PlayArrow,
                boxModifier = Modifier
                    .align(Alignment.Center)
                    .size(MyDimen.p80),
                tintColor = MyColor.White.copy(alpha = 0.8f)
            )
        }

        ExploreRightActionSection(
            modifier = Modifier.align(Alignment.BottomEnd),
            video = video,
            onCommentClick = onCommentClick
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .animateContentSize()
        ) {
            ExploreBottomInforSection(video = video)

            if (exoPlayer != null && duration > 0 && !isPlaying) {
                Slider(
                    value = position.coerceIn(0, duration).toFloat(),
                    onValueChange = { newValue ->
                        position = newValue.toLong()
                    },
                    valueRange = 0f..duration.toFloat(),
                    onValueChangeFinished = {
                        exoPlayer.seekTo(position)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MyDimen.p16,
                            end = MyDimen.p16,
                            bottom = MyDimen.p16,
                            top = MyDimen.p12
                        )
                        .height(MyDimen.p4),
                    colors = SliderDefaults.colors(
                        thumbColor = MyColor.White,
                        activeTrackColor = MyColor.White,
                        inactiveTrackColor = MyColor.Gray666.copy(alpha = 0.5f)
                    ),
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}

@Composable
fun ExploreBottomInforSection(
    modifier: Modifier = Modifier,
    video: VideoItem
) {
    Column(
        modifier = modifier
            .padding(MyDimen.p16)
            .fillMaxWidth()
    ) {
        CoreText(
            text = "@${video.username}",
            color = MyColor.White,
            style = CoreTypographyBold.displayMedium
        )

        Spacer(modifier = Modifier.height(MyDimen.p8))

        CoreText(
            text = video.description,
            color = MyColor.White,
            style = CoreTypographyBold.labelMedium,
            lineHeight = MyDimen.s20,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(MyDimen.p12))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreIcon (
                imageVector = Icons.Default.LocationOn,
                tintColor = MyColor.White,
                iconModifier = Modifier.size(MyDimen.p16)
            )

            Spacer(modifier = Modifier.width(MyDimen.p8))

            CoreText (
                text = "Original sound - ${video.username}",
                color = MyColor.White,
                style = CoreTypography.displaySmall,
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: Int,
    count: Int,
    tint: Color = MyColor.White,
    isScale: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(MyDimen.p2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MyDimen.p8)
    ) {
        CoreIcon (
            resDrawable = icon,
            tintColor = tint,
            iconModifier = Modifier
                .size(MyDimen.p30)
                .then(
                    if (isScale) Modifier.scaleOnClick(onClick)
                    else Modifier.clickable(onClick = onClick)
                )
        )

        CoreText (
            text = count.formatThousandK(),
            color = MyColor.White,
            style = CoreTypography.labelSmall,
        )
    }
}

@Composable
fun ExploreRightActionSection(
    modifier: Modifier = Modifier,
    onCommentClick: () -> Unit,
    video: VideoItem
) {
    var isLiked by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(bottom = MyDimen.p120)
            .padding(MyDimen.p16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MyDimen.p16)
    )
    {
        Box(
            modifier = Modifier
                .size(MyDimen.p50)
                .clickable { }
        ) {
            CoreImage(
                source = CoreImageSource.Url( "https://picsum.photos/100/100?random=${video.id}"),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(MyDimen.p2, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )

            CoreIcon (
                imageVector = Icons.Default.Add,
                boxModifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = MyDimen.p8)
                    .size(MyDimen.p20)
                    .background(MyColor.Red, CircleShape)
                    .padding(MyDimen.p2),
                tintColor = MyColor.White
            )
        }
        Spacer(modifier = Modifier.height(MyDimen.p8))
        ActionButton(
            icon = R.drawable.ic_heart_solid,
            count = video.likes + if (isLiked) 1 else 0,
            tint = if (isLiked) Color.Red else Color.White,
            onClick = {
                isLiked = !isLiked
            },
            isScale = true
        )

        ActionButton(
            icon = R.drawable.ic_comment_fill,
            count = video.comments,
            onClick = onCommentClick
        )

        ActionButton(
            icon = R.drawable.ic_share_fill,
            count = video.shares,
            onClick = {}
        )
    }
}