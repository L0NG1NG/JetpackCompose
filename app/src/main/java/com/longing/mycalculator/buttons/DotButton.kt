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
            val defaultText = buildAnnotatedString {
                withStyle(SpanStyle(color)) {
                    append("0${label}")
                }
            }
            data.inputText = TextFieldValue(
                defaultText,
                TextRange(defaultText.length)
            )
            return
        }

        val expression = Computer.divideExpression(inputText)
        val left = expression.first
        val right = expression.second
        val cursorIndex = left.length

        var startIndex = -1
        var endIndex = -1
        OperatorType.values().forEach {
            val i = left.text.lastIndexOf(it.label)
            if (i > startIndex) startIndex = i

            val j = right.text.lastIndexOf(it.label)
            if (j > endIndex) endIndex = j
        }

        if (endIndex < 0) {
            startIndex += 1
            endIndex = inputText.text.length
        } else {
            startIndex += 1
            endIndex += left.length
        }
        val number: String = inputText.text.substring(startIndex, endIndex)
        Log.d(TAG, "startIndex: $startIndex")
        Log.d(TAG, "endIndex: $endIndex")
        Log.d(TAG, "当前光标所在数字：${number}")
        //已经含有小数点不做处理
        if (number.contains(label)) return
        val newExpression = buildAnnotatedString {
            if (cursorIndex == inputText.text.indexOf(number)) {
                //光标刚好位于数字开头 补0
                append(left.plus(buildAnnotatedString {
                    withStyle(SpanStyle(color)) {
                        append("0")
                    }
                }))
            } else {
                append(left)
            }
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