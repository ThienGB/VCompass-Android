package com.example.vcompass.ui.core.bottombar


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyDimen

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFFF5555)
@Composable
fun CurvedBottomBar(
    modifier: Modifier = Modifier,
    barHeight: Dp = MyDimen.p56,
    bumpFraction: Float = 0.75f,
    bumpRadius: Dp = MyDimen.p48,
    containerColor: Color = Color.White,
    shadowColor: Color = Color(0x66000000),
    shadowBlur: Dp = MyDimen.p16,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val density = LocalDensity.current
    val barHeightPx = with(density) { barHeight.toPx() }
    val bumpRadiusPx = with(density) { bumpRadius.toPx() }

    val cutoutWidth = bumpRadiusPx * 1.5f  
    val cutoutRadius = bumpRadiusPx * 0.85f

    val protrusionPx = ((barHeightPx * bumpFraction)).coerceIn(0f, barHeightPx)
    val topEdgeY = protrusionPx
    val canvasHeight = with(density) { (barHeight + Dp(protrusionPx / density.density)) }

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
            val cx = size.width / 2f
            val cy = topEdgeY + cutoutRadius / 0.95f  // đáy cutout

            val left = cx - cutoutWidth
            val right = cx + cutoutWidth

            // Bezier path tạo cutout mềm mại
            val path = Path().apply {
                moveTo(0f, topEdgeY)
                lineTo(left, topEdgeY)

                // cung trái
                cubicTo(
                    left + cutoutRadius, topEdgeY,  // control 1
                    cx - cutoutRadius, cy,          // control 2
                    cx, cy                          // đáy cutout
                )

                // cung phải
                cubicTo(
                    cx + cutoutRadius, cy,          // control 1
                    right - cutoutRadius, topEdgeY, // control 2
                    right, topEdgeY
                )

                lineTo(size.width, topEdgeY)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawIntoCanvas { canvas ->
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint().apply {
                    isAntiAlias = true
                    setShadowLayer(blurPx.coerceAtLeast(1f), 0f, 0f, shadowColorInt)
                    color = android.graphics.Color.TRANSPARENT
                }
                canvas.nativeCanvas.drawPath(path.asAndroidPath(), frameworkPaint)
            }

            drawPath(path = path, color = containerColor, style = Fill)
        }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight)
                    .align(Alignment.BottomCenter),
                content = content
            )
    }
}