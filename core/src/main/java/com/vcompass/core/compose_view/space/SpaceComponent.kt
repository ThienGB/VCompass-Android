package com.vcompass.core.compose_view.space

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.vcompass.core.extensions.conditional
import com.vcompass.core.resource.MyDimen

@Composable
fun SpaceHeight(height: Dp = MyDimen.p16) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpaceWidth(width: Dp = MyDimen.p16) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun SpaceHeight24() = SpaceHeight(MyDimen.p24)

@Composable
fun SpaceHeight8() = SpaceHeight(MyDimen.p8)

@Composable
fun SpaceHeight4() = SpaceHeight(MyDimen.p4)

@Composable
fun SpaceWidth24() = SpaceWidth(MyDimen.p24)

@Composable
fun SpaceWidth8() = SpaceWidth(MyDimen.p8)

@Composable
fun SpaceWidth4() = SpaceWidth(MyDimen.p4)

@Composable
fun RowScope.ExpandableSpacer(weight: Float = 1f, fillMaxHeight: Boolean = false) {
    Spacer(
        Modifier
            .weight(weight)
            .conditional(fillMaxHeight) { fillMaxHeight() }
    )
}

@Composable
fun ColumnScope.ExpandableSpacer(weight: Float = 1f, fillMaxWith: Boolean = false) {
    Spacer(
        Modifier
            .weight(weight)
            .conditional(fillMaxWith) { fillMaxWidth() }
    )
}