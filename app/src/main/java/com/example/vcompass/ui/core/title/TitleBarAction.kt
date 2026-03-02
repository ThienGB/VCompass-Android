package com.example.vcompass.ui.core.title

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.button.BackButton
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back

@Composable
fun TitleBarAction(
    text: String = "",
    maxLines: Int = 1,
    onlyTitle: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    actionContent: (@Composable () -> Unit)? = null,
) {
    val navController: NavController = ScreenContext.navController
    val barHeight = MyDimen.p56
    val guardWidth = MyDimen.p16

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = MyDimen.p4
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .padding(end = if (actionContent != null) MyDimen.zero else MyDimen.p36),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!onlyTitle)
                leadingContent?.invoke() ?: BackButton { navController.back() }

            CoreText(
                text = text,
                style = CoreTypographySemiBold.displayMedium,
                color = MyColor.TextColorPrimary,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = guardWidth)
                    .weight(1f)
            )
            if (!onlyTitle)
                actionContent?.invoke() ?: SpaceWidth(MyDimen.p24)
        }
    }
}

@Composable
fun ActionIcon(
    resDrawable: Int? = null,
    imageVector: ImageVector? = null,
    size: Dp = MyDimen.p24,
    tintColor: Color = MyColor.Gray666,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(MyDimen.p56)
            .clip(CircleShape)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        CoreIcon(
            resDrawable = resDrawable,
            imageVector = imageVector,
            iconModifier = Modifier.size(size),
            tintColor = tintColor
        )
    }
}

@Composable
fun ActionText(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(MyDimen.p64)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        CoreText(
            text = text,
            style = CoreTypography.displayMedium.copy(fontSize = MyDimen.s14),
            color = if (enabled) MyColor.TextColorPrimary else MyColor.TextColorPrimary.copy(0.5f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(end = MyDimen.p2)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleBarActionPreview() {
    TitleBarAction(
        text = "Connections",
        actionContent = {
            ActionText(
                text = "Add",
                onClick = {},
                enabled = false
            )
        }
    )
}
