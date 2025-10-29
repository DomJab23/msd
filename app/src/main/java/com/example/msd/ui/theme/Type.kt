package com.example.msd.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Defines the default typography for the app.
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

/**
 * Creates a Typography object with a dynamic base font size.
 *
 * @param baseFontSize The base font size to be applied to the bodyLarge style.
 * Other styles are adjusted relative to this size.
 */
fun getTypography(baseFontSize: Float): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = baseFontSize.sp, // Use the dynamic font size here
            lineHeight = (baseFontSize * 1.5).sp,
            letterSpacing = 0.5.sp
        ),
        // You can also define other styles relative to the base size
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = (baseFontSize * 1.375).sp, // e.g., 22sp when base is 16sp
            lineHeight = (baseFontSize * 1.75).sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = (baseFontSize * 0.6875).sp, // e.g., 11sp when base is 16sp
            lineHeight = (baseFontSize).sp,
            letterSpacing = 0.5.sp
        )
        /* Define other text styles here using baseFontSize.sp */
    )
}
