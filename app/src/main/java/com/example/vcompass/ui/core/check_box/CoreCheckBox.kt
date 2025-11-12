package com.example.vcompass.ui.core.check_box

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vcompass.resource.CoreTypographyMedium

@Composable
fun CoreCheckBox(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.Unspecified,
    checked: Boolean = false,
    onClick: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colorScheme.primary,
            ),
            onCheckedChange = { onClick(it) },

        )
        Text(
            text = text,
            style = CoreTypographyMedium.labelMedium,
            color = textColor
        )
    }
}
