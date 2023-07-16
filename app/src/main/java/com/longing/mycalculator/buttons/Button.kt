package com.longing.mycalculator.buttons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.R
import com.longing.mycalculator.ui.theme.LightBlack

/**
 * 按钮的属性样式
 */
abstract class Button(
    val label: String,
    val fontSize: TextUnit,
    val color: Color = Color.White,
    val fontFamily: FontFamily = FontFamily(
        Font(R.font.roboto_regular)
    ),
    val backgroundColor: Color = LightBlack
) {

    abstract fun onClick(data: CalculateData)

}


