package com.example.vcompass.ui.core.snack_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.vcompass.resource.CoreTypography


@Composable
fun CustomSnackBar(snackBarData: SnackbarData) {
    val visuals = snackBarData.visuals
    val type = (visuals as? CustomSnackBarVisuals)?.type ?: SnackBarType.INFO

    val backgroundColor = when (type) {
        SnackBarType.SUCCESS -> Color(0xFF4CAF50)
        SnackBarType.WARNING -> Color(0xFFDBA500)
        SnackBarType.ERROR -> Color(0xFFF44336)
        SnackBarType.INFO -> Color(0xFF2196F3)
    }

    Snackbar(
        modifier = Modifier.padding(horizontal = 16.dp),
        action = {
            snackBarData.visuals.actionLabel?.let {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                    onClick = { snackBarData.performAction() },
                    content = {
                        Text(
                            text = it,
                            color = Color.White,
                            style = CoreTypography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = backgroundColor,
    ) {
        Text(
            text = snackBarData.visuals.message,
            maxLines = 2,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            style = CoreTypography.labelMedium,
        )
    }
}
