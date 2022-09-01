package com.longing.mycalculator.buttons

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.Computer

class NumberButton(number: Int) :
    Button(label = number.toString(), fontSize = 28.sp) {
    override fun onClick(data: CalculateData) {
        val expression = Computer.divideExpression(data.inputText)
        val currentExpression = buildAnnotatedString {
            if (data.inputText.text.isDigitsOnly()) {
                //置为白色样式
                withStyle(style = SpanStyle(color)) {
                    append(expression.first)
                    append(label)
                    append(expression.second)
                }
            } else {
                //是个表达式,使用原来的输入样式
                append(expression.first)
                withStyle(style = SpanStyle(color)) {
                    append(label)
                }
                append(expression.second)
            }
        }

        data.outputText =
            Computer.performCalculate(currentExpression.text)
        data.inputText =
            TextFieldValue(
                currentExpression,
                TextRange(expression.first.length + 1)
            )
    }
}