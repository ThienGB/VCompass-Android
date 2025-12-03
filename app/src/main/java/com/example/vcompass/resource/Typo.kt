package com.example.vcompass.resource

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vcompass.R

val CoreFontFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

val CoreTypography = Typography().copy(
    bodySmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s11,
        letterSpacing = MyDimen.sHalf
    ),
    displaySmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s12,
        letterSpacing = MyDimen.sHalf
    ),
    labelSmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s13,
        letterSpacing = MyDimen.sHalf
    ),
    labelMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s14,
        letterSpacing = MyDimen.sHalf
    ),
    labelLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s15,
        letterSpacing = MyDimen.sHalf
    ),
    displayMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s16,
        letterSpacing = MyDimen.sHalf
    ),
    displayLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s18,
        letterSpacing = MyDimen.sHalf
    ),
    bodyLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s24,
        letterSpacing = MyDimen.sHalf
    ),
    bodyMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s20,
        letterSpacing = MyDimen.sHalf
    ),
    titleLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        letterSpacing = MyDimen.sHalf
    ),
    titleMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        letterSpacing = MyDimen.sHalf
    ),
    titleSmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s18,
        letterSpacing = MyDimen.sHalf
    ),
    headlineLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s24,
        letterSpacing = MyDimen.sHalf
    ),
    headlineMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s20,
        letterSpacing = MyDimen.sHalf
    ),
    headlineSmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s19,
        letterSpacing = MyDimen.sHalf
    ),
)

val CoreTypographyMedium = Typography().copy(
    displayLarge = CoreTypography.displayLarge.copy(fontWeight = FontWeight.Medium),
    displayMedium = CoreTypography.displayMedium.copy(fontWeight = FontWeight.Medium),
    displaySmall = CoreTypography.displaySmall.copy(fontWeight = FontWeight.Medium),
    bodyLarge = CoreTypography.bodyLarge.copy(fontWeight = FontWeight.Medium),
    bodyMedium = CoreTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
    bodySmall = CoreTypography.bodySmall.copy(fontWeight = FontWeight.Medium),
    titleLarge = CoreTypography.titleLarge.copy(fontWeight = FontWeight.Medium),
    titleMedium = CoreTypography.titleMedium.copy(fontWeight = FontWeight.Medium),
    titleSmall = CoreTypography.titleSmall.copy(fontWeight = FontWeight.Medium),
    labelLarge = CoreTypography.labelLarge.copy(fontWeight = FontWeight.Medium),
    labelMedium = CoreTypography.labelMedium.copy(fontWeight = FontWeight.Medium),
    labelSmall = CoreTypography.labelSmall.copy(fontWeight = FontWeight.Medium),
    headlineLarge = CoreTypography.headlineLarge.copy(fontWeight = FontWeight.Medium),
    headlineMedium = CoreTypography.headlineMedium.copy(fontWeight = FontWeight.Medium),
    headlineSmall = CoreTypography.headlineSmall.copy(fontWeight = FontWeight.Medium)
)

val CoreTypographySemiBold = Typography().copy(
    displayLarge = CoreTypography.displayLarge.copy(fontWeight = FontWeight.SemiBold),
    displayMedium = CoreTypography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
    displaySmall = CoreTypography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
    bodyLarge = CoreTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
    bodyMedium = CoreTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
    bodySmall = CoreTypography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
    titleLarge = CoreTypography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
    titleMedium = CoreTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    titleSmall = CoreTypography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = CoreTypography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
    labelMedium = CoreTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
    labelSmall = CoreTypography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
    headlineLarge = CoreTypography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
    headlineMedium = CoreTypography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
    headlineSmall = CoreTypography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
)

val CoreTypographyBold = Typography().copy(
    displayLarge = CoreTypography.displayLarge.copy(fontWeight = FontWeight.Bold),
    displayMedium = CoreTypography.displayMedium.copy(fontWeight = FontWeight.Bold),
    displaySmall = CoreTypography.displaySmall.copy(fontWeight = FontWeight.Bold),
    bodyLarge = CoreTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
    bodyMedium = CoreTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
    bodySmall = CoreTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
    titleLarge = CoreTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
    titleMedium = CoreTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
    titleSmall = CoreTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
    labelLarge = CoreTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
    labelMedium = CoreTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
    labelSmall = CoreTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
    headlineLarge = CoreTypography.headlineLarge.copy(fontWeight = FontWeight.Bold),
    headlineMedium = CoreTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    headlineSmall = CoreTypography.headlineSmall.copy(fontWeight = FontWeight.Bold)
)