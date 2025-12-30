package com.example.vcompass.screen.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class Cloud(
    val id: Int,
    val offsetX: Float,
    val offsetY: Float,
    val size: Float,
    val speed: Float
)

data class Plane(
    val id: Int,
    val offsetX: Float,
    val offsetY: Float,
    val rotation: Float = 0f,
    val baseY: Float,
    val speed: Float,
    val wobbleOffset: Float
)

data class TapEffect(
    val id: Int,
    val position: Offset,
    val timestamp: Long
)

@Composable
fun TravelLoginAnimation() {
    var clouds by remember { mutableStateOf(generateClouds(8)) }
    var planes by remember { mutableStateOf(generatePlanes(3)) }
    var tapEffects by remember { mutableStateOf<List<TapEffect>>(emptyList()) }
    var animationSpeedMultiplier by remember { mutableStateOf(1f) }
    // Animation cho máy bay
    LaunchedEffect(Unit) {
        while (true) {
            delay(50)
            val speedFactor = animationSpeedMultiplier // Đọc hệ số tốc độ
            planes = planes.map { plane ->
                val newX = (plane.offsetX + plane.speed * speedFactor) % 1000f
                plane.copy(
                    offsetX = if (newX < 0) 1000f else newX,
                    offsetY = plane.baseY
                )
            }
        }
    }

    // Animation cho mây di chuyển
    LaunchedEffect(Unit) {
        while (true) {
            delay(50)
            val speedFactor = animationSpeedMultiplier // Đọc hệ số tốc độ
            clouds = clouds.map { cloud ->
                // Áp dụng hệ số tốc độ vào đây
                val newX = (cloud.offsetX + cloud.speed * speedFactor) % 1000f
                cloud.copy(offsetX = if (newX < 0) 1000f else newX)
            }
        }
    }

    // Xóa tap effects sau 1 giây
    LaunchedEffect(tapEffects) {
        if (tapEffects.isNotEmpty()) {
            delay(1000)
            val currentTime = System.currentTimeMillis()
            tapEffects = tapEffects.filter { currentTime - it.timestamp < 1000 }
        }
    }

    Box(
        modifier = Modifier
            .height(MyDimen.p280)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val newEffect = TapEffect(
                            id = Random.nextInt(),
                            position = offset,
                            timestamp = System.currentTimeMillis()
                        )
                        tapEffects = tapEffects + newEffect
                    },
                    onPress = {
                        animationSpeedMultiplier = 3f
                        try {
                            awaitRelease()
                        } finally {
                            animationSpeedMultiplier = 1f
                        }
                    }
                )
            }
    ) {
        // Vẽ bầu trời gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1e3a8a),
                        Color(0xFF60a5fa),
                        Color(0xFF93c5fd)
                    )
                )
            )
        }

        // Vẽ mây bằng icon
        clouds.forEach { cloud ->
            Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = null,
                modifier = Modifier
                    .offset(x = cloud.offsetX.dp, y = cloud.offsetY.dp)
                    .size(cloud.size.dp),
                tint = Color.White.copy(alpha = 0.85f)
            )
        }

        // Vẽ máy bay bằng icon
        planes.forEach { plane ->
            CoreIcon(
                resDrawable = R.drawable.ic_air_plane,
                iconModifier = Modifier
                    .offset(x = plane.offsetX.dp, y = plane.offsetY.dp)
                    .size(56.dp)
                    .graphicsLayer {
                        rotationZ = plane.rotation
                    },
                tintColor = MyColor.White
            )
        }

        // Vẽ hiệu ứng khi chạm (giữ nguyên vẽ tay đẹp)
        tapEffects.forEach { effect ->
            val age = (System.currentTimeMillis() - effect.timestamp) / 1000f
            TapEffectAnimation(effect.position, age)
        }
    }
}

@Composable
fun TapEffectAnimation(position: Offset, age: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val alpha = (1f - age).coerceIn(0f, 1f)
        val radius = age * 100f

        // Vẽ các vòng tròn lan tỏa
        for (i in 0..2) {
            val currentRadius = radius + i * 20f
            drawCircle(
                color = Color.White.copy(alpha = alpha * 0.3f),
                radius = currentRadius,
                center = position,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )
        }

        // Vẽ các ngôi sao nhỏ xung quanh
        for (i in 0..5) {
            val angle = (i * 60f + age * 360f) * Math.PI / 180f
            val starX = position.x + cos(angle).toFloat() * radius * 0.5f
            val starY = position.y + sin(angle).toFloat() * radius * 0.5f

            drawCircle(
                color = Color(0xFFFFD700).copy(alpha = alpha),
                radius = 4f,
                center = Offset(starX, starY)
            )
        }
    }
}

fun generateClouds(count: Int): List<Cloud> {
    return List(count) { i ->
        Cloud(
            id = i,
            offsetX = Random.nextFloat() * 500f,
            offsetY = Random.nextFloat() * 100f + 50f,
            size = Random.nextFloat() * 25f + 50f,
            speed = Random.nextFloat() * 0.4f + 0.4f
        )
    }
}

fun generatePlanes(count: Int): List<Plane> {
    return List(count) { i ->
        Plane(
            id = i,
            offsetX = Random.nextFloat() * 600f - 200f,
            offsetY = 0f,
            baseY = Random.nextFloat() * 70f + 50f,
            rotation = 0f,
            speed = Random.nextFloat() * 1.5f + 1.5f,
            wobbleOffset = Random.nextFloat() * 10f
        )
    }
}
