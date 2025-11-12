package com.example.vcompass.ui.core.rating

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.vcompass.core.extensions.conditional
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float = 0f,
    onRatingChanged: (Float) -> Unit = {},
    starSize: Dp = MyDimen.p36,
    readOnly: Boolean = true
) {
    val starCount = 5
    var componentWidth by remember { mutableFloatStateOf(0f) }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    componentWidth = coordinates.size.width.toFloat()
                }
                .conditional(!readOnly) {
                    pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val newRating = calculateRating(
                                offset.x,
                                componentWidth,
                                starCount
                            )
                            onRatingChanged(newRating)
                        }
                    }.pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            change.consume()
                            val newRating = calculateRating(
                                change.position.x,
                                componentWidth,
                                starCount
                            )
                            onRatingChanged(newRating)
                        }
                    }
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(starCount) { index ->
                    val starRating = when {
                        rating >= index + 1 -> 1f
                        rating > index -> rating - index
                        else -> 0f
                    }

                    StarIcon(
                        value = starRating,
                        size = starSize,
                    )
                }
            }
        }
    }
}

@Composable
fun StarIcon(
    value: Float,
    size: Dp,
) {
    Canvas(modifier = Modifier.size(size)) {
        val canvasSize = this.size.minDimension
        val path = createStarPath(canvasSize)

        drawPath(
            path = path,
            color = MyColor.Gray999,
            style = Fill
        )

        if (value > 0f) {
            clipRect(
                left = 0f,
                top = 0f,
                right = canvasSize * value,
                bottom = canvasSize
            ) {
                drawPath(
                    path = path,
                    color = MyColor.Rating,
                    style = Fill
                )
            }
        }
    }
}

private fun createStarPath(size: Float): Path {
    val path = Path()
    val centerX = size / 2f
    val centerY = size / 2f
    val outerRadius = size / 2f * 0.9f
    val innerRadius = outerRadius * 0.4f

    val angleStep = Math.PI / 5.0

    for (i in 0 until 10) {
        val angle = -Math.PI / 2 + i * angleStep
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val x = centerX + (radius * cos(angle)).toFloat()
        val y = centerY + (radius * sin(angle)).toFloat()

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    path.close()
    return path
}

fun calculateRating(x: Float, width: Float, starCount: Int): Float {
    if (width == 0f) return 0f

    val rawRating = (x / width) * starCount
    val rounded = (rawRating * 2f).roundToInt() / 2f
    return rounded.coerceIn(0f, starCount.toFloat())
}

@Preview(showSystemUi = true)
@Composable
fun RatingBarPreview(){
    var rating by remember { mutableFloatStateOf(3.5f) }
    RatingBar(rating = rating, onRatingChanged = {rating = it})
}