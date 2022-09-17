package com.longing.mycalculator.buttons

import android.util.Log
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.Computer
import com.longing.mycalculator.OperatorType

private const val TAG = "DotButton"

class DotButton : Button(label = ".", fontSize = 28.sp) {
    override fun onClick(data: CalculateData) {
        val inputText = data.inputText
        if (inputText.text.isBlank()) {
            buildAnnotatedString {
                withStyle(SpanStyle(color)) {
                    append("0$label")
                }
            }
        } else {
            //判断数字有没有小数点
            val expression = Computer.divideExpression(inputText)
            val left = expression.first
            val right = expression.second

            val number: String
            var startIndex = -1
            var endIndex = -1

            OperatorType.values().forEach {
                if (startIndex < 0) {
                    val index = left.text.lastIndexOf(it.label)
                    if (index > startIndex) startIndex = index
                }
                if (endIndex < 0) {
                    val index = right.text.lastIndexOf(it.label)
                    if (index > endIndex) endIndex = index
                }
            }
            Log.d(TAG, "startIndex: $startIndex")
            Log.d(TAG, "endIndex: $endIndex")

            if (startIndex == left.length-1) {
                //"光标前面刚好是运算符补0
                number = left.text + "0$label" + right.text

            } else if (startIndex < 0) {
                number = if (endIndex < 0) {
                    //纯纯的数字
                    inputText.text

                } else {
                    left.text + right.text.subSequence(0, endIndex)
                }
            } else {
                number = if (endIndex > 0) {
                    left.subSequence(startIndex, left.length).text + right.subSequence(
                        0,
                        endIndex
                    ).text

                } else {
                    inputText.text.subSequence(startIndex, left.length + endIndex).toString()
                }
            }

            if (number.contains(label)) {
                val newExpression = buildAnnotatedString {
                    append(left)
                    withStyle(SpanStyle(color)) {
                        append(label)
                    }
                    append(right)
                }
                data.inputText =
                    TextFieldValue(newExpression, TextRange(left.length + 1))
                data.outputText =
                    Computer.performCalculate(newExpression.text)
            }
        }
    }
}