package com.example.vcompass.ui.core.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.lerp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StickyHeaderAnimationLayout(
    coverUrl: String?,
    imageHeightRatio: Float = 0.3f,
    tabSpaceRatio: Float = 0.25f,
    contentSpaceRatio: Float = 0.18f,
    maxProgressRatio: Float = 0.3f,
    imageSection: (@Composable (imageHeight: Dp) -> Unit)? = null,
    headerSection: @Composable (autoProgress: Float) -> Unit,
    infoSection: @Composable (
        autoProgress: Float,
        avatarOffsetX: Dp,
        avatarOffsetY: Dp
    ) -> Unit,
    contentSection: @Composable (
        autoProgress: Float,
        containerSpace: Dp,
        nestedScrollConnection: NestedScrollConnection,
        contentScrollState: ScrollState
    ) -> Unit
) {
    val windowInfo = LocalWindowInfo.current
    val screenHeight = with(LocalDensity.current) { windowInfo.containerSize.height.toDp() }

    val imageHeight = screenHeight * imageHeightRatio
    val containerSpace = screenHeight * tabSpaceRatio
    val contentSpace = screenHeight * contentSpaceRatio
    val maxProgress = screenHeight * maxProgressRatio

    // Scroll logic
    val maxOffset = with(LocalDensity.current) { maxProgress.toPx() }
    val scope = rememberCoroutineScope()
    val scrollOffset = rememberSaveable(
        saver = Saver(save = { it.value }, restore = { Animatable(it) })
    ) { Animatable(0f) }
    val contentScrollState = rememberScrollState()

    val autoProgress by remember {
        derivedStateOf { (scrollOffset.value / maxOffset).coerceIn(0f, 1f) }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (scrollOffset.value - delta).coerceIn(0f, maxOffset)
                return if (delta < 0) {
                    if (scrollOffset.value < maxOffset) {
                        scope.launch { scrollOffset.snapTo(newOffset) }
                        Offset(0f, available.y)
                    } else Offset.Zero
                } else {
                    if (contentScrollState.value > 0) Offset.Zero else {
                        scope.launch { scrollOffset.snapTo(newOffset) }
                        Offset(0f, available.y)
                    }
                }
            }
        }
    }

    // Auto expand / collapse
    LaunchedEffect(contentScrollState.isScrollInProgress) {
        if (!contentScrollState.isScrollInProgress && autoProgress in 0f..1f) {
            delay(100)
            val target = if (autoProgress < 0.3f) 0f else maxOffset
            scrollOffset.animateTo(
                targetValue = target,
                animationSpec = tween(durationMillis = 400, easing = LinearEasing)
            )
        }
    }

    // Derived offsets
    val avatarOffsetX = lerp(MyDimen.zero, MyDimen.p40, autoProgress)
    val avatarOffsetY = lerp(
        (contentSpace * (1 - autoProgress)) + MyDimen.p16,
        MyDimen.zero,
        autoProgress
    )


    Box(modifier = Modifier.fillMaxSize()) {
        // Header
        headerSection(autoProgress)

        // Cover image
        if (imageSection != null) {
            imageSection(imageHeight)
        } else {
            CoreImage(
                source = CoreImageSource.Url(coverUrl ?: ""),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColor.Gray999)
                    .height(imageHeight)
            )
        }

        // Info section (avatar, name, etc.)
        infoSection(autoProgress, avatarOffsetX, avatarOffsetY)

        // Main content (tabs, scrollable section)
        contentSection(autoProgress, containerSpace, nestedScrollConnection, contentScrollState)
    }
}