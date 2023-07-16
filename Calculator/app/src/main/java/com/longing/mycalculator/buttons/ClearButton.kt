package com.longing.mycalculator.buttons

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.ui.theme.Red

class ClearButton : Button(label = "C", fontSize = 28.sp, color = Red) {
    override fun onClick(data: CalculateData) {
        data.inputText = TextFieldValue()
        data.outputText = ""
    }
}