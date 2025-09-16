package com.vcompass.core.compose_view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcompass.core.R


internal const val switchButtonWidth = 38
internal const val switchButtonHeight = 18
internal const val switchTrackSize= 18
internal val stopValue = switchButtonWidth - switchTrackSize

@Composable
fun SwitchButton(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "SwitchAnimation"
    )
    val currentColor = if (isChecked)
        colorResource(R.color.colorSecondaryLight)
    else
        colorResource(R.color.textColorLightWhite)
    val animatedTrackColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "TrackColorAnimation"
    )
    Surface(
        modifier = modifier.width(switchButtonWidth.dp).height(switchButtonHeight.dp),
        shape = RoundedCornerShape(99.dp),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(99.dp))
                    .background(animatedTrackColor)
                    .clickable { onCheckedChange(!isChecked) }
            )

            val offsetValue = lerp(0f, stopValue.toFloat(), animatedProgress).dp
            Box(
                modifier = Modifier
                    .size(switchTrackSize.dp)
                    .padding(2.dp)
                    .offset(x = offsetValue)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}


@Preview(showSystemUi = true)
@Composable
fun OnOffSwitchPreview() {
    var isChecked by remember { mutableStateOf(false) }
    SwitchButton(
        isChecked = isChecked,
        onCheckedChange = { isChecked = it },
        modifier = Modifier.padding(16.dp)
    )
}