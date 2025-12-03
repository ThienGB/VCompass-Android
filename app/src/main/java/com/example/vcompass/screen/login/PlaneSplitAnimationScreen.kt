package com.example.vcompass.screen.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.resource.MyColor
import kotlinx.coroutines.delay
import com.example.vcompass.R

/**
 * Màn hình animation máy bay chia đôi màn hình
 *
 * @param onAnimationComplete Callback khi animation hoàn thành
 * @param planeIcon Resource ID của icon máy bay (có thể dùng drawable hoặc vector)
 * @param topScreenColor Màu của nửa màn hình trên
 * @param bottomScreenColor Màu của nửa màn hình dưới
 * @param animationDuration Thời gian animation (ms)
 */
@Composable
fun PlaneSplitAnimationScreen(
    planeIcon: Int? = null,
    topScreenColor: Color = Color(0xFF1976D2),
    bottomScreenColor: Color = Color(0xFF64B5F6),
    animationDuration: Long = 2500L
) {
    var animationState by remember { mutableStateOf(AnimationState.INITIAL) }

    // Animation cho máy bay bay từ trái sang phải
    val planeProgress by animateFloatAsState(
        targetValue = when (animationState) {
            AnimationState.INITIAL -> -0.2f
            AnimationState.PLANE_FLYING -> 1.2f
            AnimationState.SCREEN_SPLITTING -> 1.2f
            AnimationState.COMPLETED -> 1.2f
        },
        animationSpec = tween(
            durationMillis = (animationDuration * 0.4).toInt(), // 40% thời gian cho máy bay
            easing = LinearEasing
        ),
        label = "PlaneProgress"
    )

    // Animation cho nửa trên màn hình
    val topScreenOffset by animateFloatAsState(
        targetValue = when (animationState) {
            AnimationState.INITIAL, AnimationState.PLANE_FLYING -> 0f
            AnimationState.SCREEN_SPLITTING, AnimationState.COMPLETED -> -1f
        },
        animationSpec = tween(
            durationMillis = (animationDuration * 0.4).toInt(), // 40% thời gian cho split
            easing = FastOutSlowInEasing
        ),
        label = "TopScreenOffset"
    )

    // Animation cho nửa dưới màn hình
    val bottomScreenOffset by animateFloatAsState(
        targetValue = when (animationState) {
            AnimationState.INITIAL, AnimationState.PLANE_FLYING -> 0f
            AnimationState.SCREEN_SPLITTING, AnimationState.COMPLETED -> 1f
        },
        animationSpec = tween(
            durationMillis = (animationDuration * 0.4).toInt(),
            easing = FastOutSlowInEasing
        ),
        label = "BottomScreenOffset"
    )

    // Điều khiển flow của animation
    LaunchedEffect(Unit) {
        // Bắt đầu máy bay bay
        animationState = AnimationState.PLANE_FLYING

        // Đợi máy bay bay qua giữa màn hình
        delay((animationDuration * 0.3).toLong())

        // Bắt đầu chia màn hình
        animationState = AnimationState.SCREEN_SPLITTING

        // Đợi animation hoàn thành
        delay((animationDuration * 0.5).toLong())

        // Chuyển sang màn hình tiếp theo
        animationState = AnimationState.COMPLETED
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColor.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    translationY = topScreenOffset * size.height
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            topScreenColor,
                            topScreenColor.copy(alpha = 0.8f)
                        )
                    )
                )
                .zIndex(2f)
        )

        // Nửa dưới màn hình
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .graphicsLayer {
                    translationY = bottomScreenOffset * size.height
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            bottomScreenColor.copy(alpha = 0.8f),
                            bottomScreenColor
                        )
                    )
                )
                .zIndex(2f)
        )

        // Máy bay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(3f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationX = planeProgress * size.width
                    }
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_air_plane,
                    iconModifier = Modifier
                        .size(80.dp)
                        .rotate(-15f),
                    tintColor = MyColor.White
                )
            }
        }
    }
}

/**
 * Enum để quản lý các state của animation
 */
private enum class AnimationState {
    INITIAL,
    PLANE_FLYING,
    SCREEN_SPLITTING,
    COMPLETED
}
