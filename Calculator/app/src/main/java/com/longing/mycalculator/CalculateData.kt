package com.longing.mycalculator

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

val LocalCalculateData = compositionLocalOf<CalculateData> { error("no calculate data provided") }

class CalculateData {
    var inputText by mutableStateOf(TextFieldValue())
    var outputText by mutableStateOf("")
}