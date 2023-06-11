package com.longing.bloom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.longing.bloom.R

private val NunitoFamily = FontFamily(
    Font(R.font.nunitosans_light, FontWeight.Light),
    Font(R.font.nunitosans_semibold, FontWeight.SemiBold),
    Font(R.font.nunitosans_bold, FontWeight.Bold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val h1 = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 18.sp,
)
val h2 = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 14.sp,
    letterSpacing = 0.15.sp,
)
val subtitle1 = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 16.sp,
)
val body1 = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 14.sp,
)
val body2 = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 12.sp,
)
val button = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 14.sp,
    letterSpacing = 1.sp,
)
val caption = TextStyle(
    fontFamily = NunitoFamily,
    fontSize = 12.sp,
)



