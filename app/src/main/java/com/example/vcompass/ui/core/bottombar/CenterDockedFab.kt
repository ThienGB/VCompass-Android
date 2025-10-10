package com.example.vcompass.ui.core.bottombar

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.vcompass.R
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.resource.MyColor

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CenterDockedFab(
    modifier: Modifier = Modifier,
    barHeight: Dp = MyDimen.p56,
    cutoutRadius: Dp = MyDimen.p36,
    onClick: () -> Unit = {}
) {
    val density = LocalDensity.current

    val offsetY = with(density) {
        val fabR = barHeight / 1.65f
        val cutR = cutoutRadius
        val overlap = (fabR - cutR + MyDimen.p10).toPx() + (barHeight / 2).toPx()
        -(barHeight.toPx() - overlap)
    }

    FloatingActionButton(
        onClick = onClick,
        containerColor = MyColor.Primary,
        shape = RoundedCornerShape(MyDimen.p32),
        modifier = modifier.size(MyDimen.p52)
            .offset(y = Dp(offsetY / density.density)),
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = MyDimen.zero),
    ) {
        Icon(painter = painterResource(R.drawable.ic_square_plus), contentDescription = "", tint = MyColor.White,
            modifier = Modifier.size(MyDimen.p24))
    }
}