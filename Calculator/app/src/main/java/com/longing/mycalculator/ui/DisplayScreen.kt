package com.longing.mycalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.LocalCalculateData
import com.longing.mycalculator.R
import com.longing.mycalculator.ui.theme.CyanBlue
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.MyCalculatorTheme


@Composable
fun DisplayScreen(modifier: Modifier) {
    //remember保证下次重组时数据不进行改变
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val robotFontFamily by remember {
        mutableStateOf(
            FontFamily(
                Font(R.font.roboto_light)
            )
        )
    }
    val textSelectionColors by remember {
        mutableStateOf(
            TextSelectionColors(
                handleColor = CyanBlue,
                backgroundColor = LightGreen.copy(alpha = 0.3f)
            )
        )
    }
    val calculatorData = LocalCalculateData.current
    //TODO ↓ ？？
    val fontSize = when (calculatorData.inputText.text.length) {
        in 0..12 -> {
            40.sp
        }
        in 13..16 -> {
            30.sp
        }
        else -> 24.sp
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
            .padding(start = 16.dp, end = 16.dp)

    ) {

        CompositionLocalProvider(
            LocalTextInputService provides null,
            LocalTextSelectionColors provides textSelectionColors,
        ) {


            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                BasicTextField(
                    value = calculatorData.inputText,
                    onValueChange = {
                        calculatorData.inputText = it
                    },
                    textStyle = TextStyle(
                        fontSize = fontSize,
                        fontFamily = robotFontFamily,
                        textAlign = TextAlign.End
                    ),
                    maxLines = 3,
                    cursorBrush = SolidColor(CyanBlue),
                    modifier = Modifier
                        .focusRequester(focusRequester),
                )
            }
        }
        Text(
            text = calculatorData.outputText, fontSize = 24.sp,
            fontFamily = robotFontFamily,
            color = Color.Gray,
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }
    LaunchedEffect(Unit) {
        //默认让textFiled有光标在闪
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun PrevDisplayScreen() {
    MyCalculatorTheme {
        val calculateData = CalculateData().apply {
            inputText = TextFieldValue("123+4567")
            outputText = "12345567"
        }

        CompositionLocalProvider(
            LocalCalculateData provides calculateData
        ) {
            DisplayScreen(modifier = Modifier.fillMaxSize())
        }
    }
}