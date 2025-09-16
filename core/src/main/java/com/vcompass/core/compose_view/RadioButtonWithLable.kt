package com.vcompass.core.compose_view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@Composable
fun RadioButtonWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val selectedBaseColor = colorResource(id = R.color.colorSecondaryDark)
    val unselectedBaseColor = colorResource(id = R.color.textColorDark)
    val disabledColor = Color.Gray

    val radioColor by animateColorAsState(
        targetValue = if (selected) selectedBaseColor else unselectedBaseColor,
        animationSpec = tween(durationMillis = 300)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(
            selected = selected, onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = radioColor,
                unselectedColor = radioColor,
                disabledSelectedColor = disabledColor,
                disabledUnselectedColor = disabledColor
            ),
            modifier = modifier.size(35.dp)
        )
        Text(
            text = label,
            style = CoreTypography.displayMedium,
            fontSize = 15.sp,
            color = colorResource(id = R.color.textColorDark),
            fontWeight = FontWeight.W500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonWithLabelPreview() {
    var selectedOption by remember { mutableStateOf("Option 1") }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        listOf("Option 1", "Option 2", "Option 3").forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selectedOption = option }
            ) {
                RadioButtonWithLabel(
                    label = option,
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option }
                )
            }
        }
    }
}

