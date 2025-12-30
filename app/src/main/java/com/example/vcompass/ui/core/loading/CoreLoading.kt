package com.example.vcompass.ui.core.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyDimen
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CoreLoading(
    modifier: Modifier = Modifier,
    size: Dp = MyDimen.p56,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 15,
    sweepAngleDeg: Float = 180f,
    maxDotRadiusDp: Dp = MyDimen.p4,
    minDotRadiusFraction: Float = 0.3f,
    rotationSpeedDegPerSec: Float = 360f
) {
    val transition = rememberInfiniteTransition(label = "sweep-dot-spin")
    val headAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ((360f / rotationSpeedDegPerSec) * 1000f).toInt(),
                easing = LinearEasing
            )
        ),
        label = "headAngle"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val centerX = center.x
        val centerY = center.y

        val ringRadiusPx = size.toPx() / 2f - maxDotRadiusDp.toPx() * 2f

        val maxDotRadiusPx = maxDotRadiusDp.toPx()
        val minDotRadiusPx = maxDotRadiusPx * minDotRadiusFraction
        val stepDeg = 360f / dotCount

        for (i in 0 until dotCount) {
            val dotAngle = i * stepDeg // độ
            val delta = angleDeltaCCW(headAngle, dotAngle)

            if (delta in 0f..sweepAngleDeg) {
                val t = delta / sweepAngleDeg
                val dotRadiusPx = lerpFloat(
                    start = maxDotRadiusPx,
                    end = minDotRadiusPx,
                    fraction = t
                )

                val alpha = 1f - t // linear fade

                val rad = Math.toRadians(dotAngle.toDouble())
                val cx = centerX + cos(rad).toFloat() * ringRadiusPx
                val cy = centerY + sin(rad).toFloat() * ringRadiusPx

                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = dotRadiusPx,
                    center = Offset(cx, cy)
                )
            }
        }
    }
}

private fun angleDeltaCCW(headDeg: Float, targetDeg: Float): Float {
    val raw = (targetDeg - headDeg) % 360f
    val norm = if (raw < 0f) raw + 360f else raw
    return norm
}

private fun lerpFloat(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

