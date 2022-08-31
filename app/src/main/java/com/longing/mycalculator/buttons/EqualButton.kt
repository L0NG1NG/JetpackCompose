package com.longing.mycalculator.buttons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.Computer
import com.longing.mycalculator.ui.theme.LightGreen

class EqualButton:Button( label = "=",
    fontSize = 36.sp,
    color = Color.White,
    backgroundColor = LightGreen
) {
    override fun onClick(data: CalculateData) {
        data.outputText = ""
        val result =
            Computer.performCalculate(data.inputText.text).let { str ->
                buildAnnotatedString {
                    withStyle(style = SpanStyle(LightGreen)) {
                        append(str)
                    }
                }
            }

        data.inputText = TextFieldValue(result, TextRange(result.length))
    }
}