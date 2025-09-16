package com.example.vcompass.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreMenuHomeBottomSheet(
    showSheet: Boolean = false,
    onDismiss: () -> Unit
) {

    // Hiển thị sheet khi showSheet = true
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss.invoke() }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("📄 This is a bottom sheet", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { onDismiss.invoke() }) {
                    Text("Close")
                }
            }
        }
    }
}
