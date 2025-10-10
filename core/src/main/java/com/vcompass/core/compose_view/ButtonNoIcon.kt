package com.vcompass.core.compose_view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.resource.MyColor
import com.vcompass.core.typography.CoreTypography

@Composable
fun ButtonNoIcon(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isFilled: Boolean = true,
    fillColor: Color = MyColor.Primary,
    borderColor: Color = MyColor.Primary,
    contentColor: Color = Color.White,
    disabledFillColor: Color = MyColor.Gray999,
    disabledBorderColor: Color = Color.LightGray,
    disabledContentColor: Color = Color.DarkGray,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        !enabled && isFilled -> disabledFillColor
        enabled && isFilled -> fillColor
        else -> Color.Transparent
    }

    val contentColorFinal = when {
        enabled && isFilled -> contentColor
        !enabled && isFilled -> disabledContentColor
        else -> fillColor
    }

    val borderModifier = modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp)
        .height(45.dp)
        .let {
            if (isFilled) it
            else it.border(
                width = 1.dp,
                color = if (enabled) borderColor else disabledBorderColor,
                shape = RoundedCornerShape(5.dp)
            )
        }

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColorFinal,
            disabledContainerColor = disabledFillColor,
            disabledContentColor = disabledContentColor
        ),
        modifier = borderModifier
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = W600,
            style = CoreTypography.bodyMedium,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ButtonNoIconPreview() {
    Row {
        ButtonNoIcon(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            text = "Cancel",
            onClick = { },
            enabled = true,
            isFilled = false,
        )
        ButtonNoIcon(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            text = "Create",
            onClick = { },
            enabled = false,
            isFilled = true,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun OneButtonNoIconPreview() {
    Column {
        ButtonNoIcon(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "Cancel",
            onClick = { },
            enabled = true,
            isFilled = false,
        )
        ButtonNoIcon(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "Submit",
            onClick = { },
            enabled = false,
            isFilled = true,
        )
        ButtonNoIcon(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "Submit",
            onClick = { },
            enabled = true,
            isFilled = true,
        )
    }
}
