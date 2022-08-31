package com.longing.mycalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.LocalCalculateData
import com.longing.mycalculator.robotFontFamily
import com.longing.mycalculator.textSelectionColors
import com.longing.mycalculator.ui.theme.CyanBlue


@Composable
fun DisplayScreen() {
    //remember保证下次重组时数据不进行改变
    val focusRequester = remember { FocusRequester() }
    val calculatorData = LocalCalculateData.current
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        CompositionLocalProvider(
            LocalTextInputService provides null,
            LocalTextSelectionColors provides textSelectionColors,
        ) {
            BasicTextField(
                value = calculatorData.inputText,
                onValueChange = {
                    calculatorData.inputText = it
                },
                textStyle = TextStyle(
                    lineHeight = 40.sp,
                    fontSize = 46.sp,
                    fontFamily = robotFontFamily,
                    textAlign = TextAlign.End
                ),
                maxLines = 3,
                cursorBrush = SolidColor(CyanBlue),
                modifier = Modifier
                    .focusRequester(focusRequester),
            )
        }
        Text(
            text = calculatorData.outputText, fontSize = 24.sp,
            fontFamily = robotFontFamily,
            color = Color.White,
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }
    LaunchedEffect(Unit) {
        //默认让textFiled有光标在闪
        focusRequester.requestFocus()
    }
}