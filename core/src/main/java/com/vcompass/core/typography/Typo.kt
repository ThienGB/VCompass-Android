package com.vcompass.core.typography

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.dimen.MyDimen

val CoreFontFamily = FontFamily(
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
    Font(R.font.quicksand_bold, FontWeight.Bold),
)

val CoreTypography = Typography().copy(
    displayLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s20,
        letterSpacing = MyDimen.sHalf
    ),
    displayMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s16,
        letterSpacing = MyDimen.sHalf
    ),
    displaySmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s12,
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
    bodySmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s16,
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
    labelLarge = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s16,
        letterSpacing = MyDimen.sHalf
    ),
    labelMedium = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s14,
        letterSpacing = MyDimen.sHalf
    ),
    labelSmall = TextStyle(
        fontFamily = CoreFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = MyDimen.s12,
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
        fontSize = MyDimen.s16,
        letterSpacing = MyDimen.sHalf
    ),
)