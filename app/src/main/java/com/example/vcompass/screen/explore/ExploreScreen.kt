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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.util.add
import com.vcompass.core.resource.MyColor
import com.example.vcompass.util.clickNoRipple
import com.example.vcompass.util.scaleOnClick
import com.vcompass.core.compose_view.AccessedBottomSheet
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.image.CoreImage
import com.vcompass.core.compose_view.image.CoreImageSource
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographyBold
import com.vcompass.core.typography.CoreTypographySemiBold
import com.vcompass.presentation.util.CoreRoute


@Preview(showSystemUi = true)
@Composable
fun ExploreScreen(
    navController: NavController = rememberNavController(),
) {
    val bottomSheetState = remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val videos = remember {
        listOf(
            VideoItem(
                id = 1,
                username = "user123",
                description = "Amazing sunset view! 🌅 #sunset #nature #beautiful",
                likes = 1234,
                comments = 89,
                shares = 45,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=1"
            ),
            VideoItem(
                id = 2,
                username = "coolcreator",
                description = "Dance challenge! Who's next? 💃 #dance #viral #challenge",
                likes = 5678,
                comments = 234,
                shares = 123,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=2"
            ),
            VideoItem(
                id = 3,
                username = "foodlover",
                description = "Recipe of the day! Easy and delicious 🍕 #food #recipe #cooking",
                likes = 890,
                comments = 67,
                shares = 34,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=3"
            ),
            VideoItem(
                id = 4,
                username = "traveler_life",
                description = "Hidden gem in Vietnam 🇻🇳 #travel #vietnam #explore",
                likes = 2341,
                comments = 156,
                shares = 78,
                videoUrl = "https://example.com/video4.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=4"
            ),
            VideoItem(
                id = 5,
                username = "petlover",
                description = "Cutest cat ever! 🐱 #cat #pets #cute #adorable",
                likes = 3456,
                comments = 189,
                shares = 234,
                videoUrl = "https://example.com/video5.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=5"
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { videos.size })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = MyDimen.p56)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .padding(horizontal = MyDimen.p8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoreIcon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tintColor = MyColor.White,
                iconModifier = Modifier.size(MyDimen.p32)
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = MyDimen.p16)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                TransparentTabItem(
                    text = "Đang theo dõi",
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                Spacer(modifier = Modifier.width(MyDimen.p24))
                TransparentTabItem(
                    text = "Đề xuất",
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }

            CoreIcon(
                imageVector = Icons.Default.Search,
                tintColor = MyColor.White,
                iconModifier = Modifier.size(MyDimen.p28),
                onClick = {navController.add(CoreRoute.ExploreSearchGraph.route)}
            )
        }
        when (selectedTab) {
            0 -> {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    VideoPlayerScreen(
                        video = videos[page],
                        isActive = pagerState.currentPage == page,
                        onLikeClick = { /* Handle like */ },
                        onCommentClick = { bottomSheetState.value = true },
                        onShareClick = { /* Handle share */ },
                        onProfileClick = { /* Handle profile click */ }
                    )
                }
            }

            1 -> {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    VideoPlayerScreen(
                        video = videos[page],
                        isActive = pagerState.currentPage == page,
                        onLikeClick = { /* Handle like */ },
                        onCommentClick = { bottomSheetState.value = true },
                        onShareClick = { /* Handle share */ },
                        onProfileClick = { /* Handle profile click */ }
                    )
                }
            }
        }
    }

    AccessedBottomSheet(
        bottomSheetState = bottomSheetState,
        sheetContent = {
            Column {

            }
        }
    )
}

@Composable
fun TransparentTabItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoreText (
            text = text,
            style = if (selected) CoreTypographySemiBold.labelLarge else CoreTypography.labelLarge,
            color = if (selected) MyColor.White else MyColor.Gray999,
            modifier = Modifier
                .clip(RoundedCornerShape(MyDimen.p6))
                .clickable { onClick() }
                .padding(horizontal = MyDimen.p12, vertical = MyDimen.p6)
        )
        if (selected) {
            HorizontalDivider(
                thickness = MyDimen.p2,
                color = MyColor.White,
                modifier = Modifier
                    .width(MyDimen.p80)
                    .padding(top = MyDimen.p4)
            )
        }

    }
}


@OptIn(UnstableApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
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

@Composable
fun ExploreBottomInforSection(
    modifier: Modifier = Modifier,
    video: VideoItem
) {
    Column(
        modifier = modifier
            .padding(MyDimen.p16)
            .fillMaxWidth()
    )
    {
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
                .size(MyDimen.p32)
                .then(
                    if (isScale) Modifier.scaleOnClick(onClick)
                    else Modifier.clickable(onClick = onClick)
                )
        )

        CoreText (
            text = formatCount(count),
            color = MyColor.White,
            style = CoreTypography.labelSmall,
        )
    }
}

fun formatCount(count: Int): String {
    return when {
        count >= 1000000 -> "${count / 1000000}M"
        count >= 10000 -> "${count / 1000}K"
        else -> count.toString()
    }
}

data class VideoItem(
    val id: Int,
    val username: String,
    val description: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val videoUrl: String,
    val thumbnailUrl: String
)