package com.example.vcompass.ui.core.bottombar


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.vcompass.core.dimen.MyDimen

@Composable
fun CurvedBottomBar(
    modifier: Modifier = Modifier,
    barHeight: Dp,
    bumpFraction: Float = 0.75f,
    bumpRadius: Dp = MyDimen.p48,
    cornerRadius: Dp,
    containerColor: Color,
    contentColor: Color,
    shadowColor: Color = Color(0x66000000),
    shadowBlur: Dp = MyDimen.p24,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val barHeightPx = with(density) { barHeight.toPx() }
    val cornerPx = with(density) { cornerRadius.toPx() }
    val bumpRadiusPx = with(density) { bumpRadius.toPx() }

    val protrusionPx = ((barHeightPx * bumpFraction)).coerceIn(0f, barHeightPx)
    val topEdgeY = protrusionPx
    val canvasHeight = with(density) { (barHeight + Dp(protrusionPx / density.density)) }

    // Shadow option
    val blurPx = with(density) { shadowBlur.toPx() }
    val shadowColorInt = android.graphics.Color.argb(
        (shadowColor.alpha * 255).toInt(),
        (shadowColor.red * 255).toInt(),
        (shadowColor.green * 255).toInt(),
        (shadowColor.blue * 255).toInt()
    )

    Box(modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(canvasHeight)
        ) {

            val rectPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = 0f,
                        top = topEdgeY,
                        right = size.width,
                        bottom = size.height,
                        topLeftCornerRadius = CornerRadius(cornerPx, cornerPx),
                        topRightCornerRadius = CornerRadius(cornerPx, cornerPx),
                    )
                )
            }

            val cx = size.width / 2f
            val cy = topEdgeY + (bumpRadiusPx - protrusionPx)

            val circlePath = Path().apply {
                addOval(
                    Rect(
                        cx - bumpRadiusPx, cy - bumpRadiusPx,
                        cx + bumpRadiusPx, cy + bumpRadiusPx
                    )
                )
            }
            val bumped = Path.combine(PathOperation.Union, rectPath, circlePath)


            drawIntoCanvas { canvas ->
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint().apply {
                    isAntiAlias = true
                    setShadowLayer(
                        blurPx.coerceAtLeast(1f),
                        0f,
                        0f,
                        shadowColorInt
                    )
                    color = android.graphics.Color.TRANSPARENT
                }
                canvas.nativeCanvas.drawPath(bumped.asAndroidPath(), frameworkPaint)
            }

            drawPath(bumped, color = containerColor, style = Fill)
        }

        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight)
                    .align(Alignment.BottomCenter),
                content = content
            )
        }
    }
}