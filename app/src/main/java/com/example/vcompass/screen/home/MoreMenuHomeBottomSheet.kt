package com.example.vcompass.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.example.vcompass.util.clickNoRipple
import com.vcompass.presentation.util.CoreRoute
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ArcMenu(
    expanded: Boolean,
    onDismiss: () -> Unit
) {
    val navController = ScreenContext.navController
    val items = listOf(
        Icons.Default.VideoLibrary,
        Icons.Default.AddLocation,
        Icons.Default.PostAdd,
    )
    val itemActions = listOf(
        { navController.navigate(CoreRoute.CreateSchedule.route) },
        { navController.navigate(CoreRoute.CreateSchedule.route) },
        { navController.navigate(CoreRoute.CreateSchedule.route) }
    )

    val radius = 120f

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        // ===== Background overlay =====
        AnimatedVisibility(expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickNoRipple {
                        onDismiss()
                    }
            )
        }

        // ===== Radial items =====
        items.forEachIndexed { index, icon ->

            val angle = 180f / (items.size - 1) * index
            val rad = Math.toRadians(angle.toDouble())

            val offsetX by animateFloatAsState(
                targetValue = if (expanded) (radius * cos(rad)).toFloat() else 0f,
                animationSpec = spring(
                    dampingRatio = 0.6f,
                    stiffness = Spring.StiffnessLow
                ),
                label = ""
            )

            val offsetY by animateFloatAsState(
                targetValue = if (expanded) -(radius * sin(rad)).toFloat() else 0f,
                animationSpec = spring(
                    dampingRatio = 0.6f,
                    stiffness = Spring.StiffnessLow
                ),
                label = ""
            )

            val scale by animateFloatAsState(
                if (expanded) 1f else 0f,
                label = ""
            )

            val alpha by animateFloatAsState(
                if (expanded) 1f else 0f,
                label = ""
            )

            FloatingActionButton(
                onClick = { itemActions[index]() },
                modifier = Modifier
                    .offset {
                        IntOffset(
                            offsetX.roundToInt(),
                            offsetY.roundToInt()
                        )
                    }
                    .padding(bottom = MyDimen.p56)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .size(48.dp),
                containerColor = MyColor.White
            ) {
                Icon(icon, contentDescription = null)
            }
        }
    }
}
