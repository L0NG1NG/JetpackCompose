package com.longing.mycalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class ScreenData {
    var inputText by mutableStateOf(TextFieldValue())
    var outputText by mutableStateOf("")

}