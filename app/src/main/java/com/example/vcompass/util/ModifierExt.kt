package com.example.vcompass.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vcompass.resource.MyDimen
import kotlinx.coroutines.launch

fun Modifier.clickableWithScale(
    scaleDown: Float = 0.95f,
    animationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 120),
    onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = animationSpec,
        label = "ClickScale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
}

fun Modifier.rippleClickable(
    radius: Dp = MyDimen.p16,
    bounded: Boolean = false,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(bounded = bounded, radius = radius),
        onClick = onClick
    )
}


@Composable
fun Modifier.scaleOnClick(onClick: () -> Unit): Modifier {
    val scope = rememberCoroutineScope()
    val scaleAnim = remember { Animatable(1f) }

    return this
        .scale(scaleAnim.value)
        .clickNoRipple {
            scope.launch {
                scaleAnim.snapTo(1f)
                scaleAnim.animateTo(0.85f, animationSpec = tween(100))
                scaleAnim.animateTo(1.2f, animationSpec = tween(100))
                scaleAnim.animateTo(1f, animationSpec = tween(200))
            }
            onClick()
        }
}

fun Modifier.clickNoRipple(onClick: () -> Unit): Modifier {
    return this.then(
        Modifier.clickable(
            indication = null,
            interactionSource = MutableInteractionSource(),
            onClick = onClick
        )
    )
}

inline fun Modifier.conditional(
    condition: Boolean,
    crossinline block: Modifier.() -> Modifier
): Modifier = if (condition) then(block(Modifier)) else this

inline fun <T> Modifier.optional(
    value: T?,
    crossinline block: Modifier.(T) -> Modifier
): Modifier = value?.let { block(it) } ?: this

fun Modifier.glassmorphism(
    shape: Shape = CircleShape,
    clip: Shape = CircleShape,
    color: Color = Color.White,
    gradientColors: List<Color> = listOf(
        Color.White.copy(alpha = 0.4f),
        Color.White.copy(alpha = 0.1f)
    ),
    backgroundAlpha: Float = 0.2f,
    borderWidth: Dp = MyDimen.p1,
    highlightAlpha: Float = 0.35f
): Modifier {
    return this
        .clip(clip)
        .background(
            color = color.copy(alpha = backgroundAlpha),
            shape = shape
        )
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(colors = gradientColors),
            shape = shape
        )
        .drawWithCache {
            val glowBrush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = highlightAlpha),
                    Color.Transparent
                ),
                radius = size.minDimension
            )
            onDrawWithContent {
                drawRect(brush = glowBrush, size = size)
                drawContent()
            }
        }
}