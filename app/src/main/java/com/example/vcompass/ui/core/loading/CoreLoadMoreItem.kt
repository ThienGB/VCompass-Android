package com.example.vcompass.ui.core.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.space.SpaceWidth

@Composable
fun CoreLoadMoreItem(
    modifier: Modifier = Modifier,
    size: Dp = MyDimen.p44,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 11,
    sweepAngleDeg: Float = 240f,
    maxDotRadiusDp: Dp = MyDimen.p4,
    minDotRadiusFraction: Float = 0.2f,
    rotationSpeedDegPerSec: Float = 360f
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MyDimen.p16),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreLoading(
            size = size,
            color = color,
            dotCount = dotCount,
            sweepAngleDeg = sweepAngleDeg,
            maxDotRadiusDp = maxDotRadiusDp,
            minDotRadiusFraction = minDotRadiusFraction,
            rotationSpeedDegPerSec = rotationSpeedDegPerSec,
        )
        SpaceWidth()
        Text(stringResource(R.string.lb_loading_data), style = CoreTypographyMedium.labelMedium)
    }
}