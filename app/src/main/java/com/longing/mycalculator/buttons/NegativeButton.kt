package com.longing.mycalculator.buttons

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.Computer
import com.longing.mycalculator.OperatorType

//将原来数字变为负数
class NegativeButton : Button("+/–", fontSize = 22.sp) {
    override fun onClick(data: CalculateData) {
        val label = "-"
        val inputText = data.inputText
        if (inputText.text.isBlank()) {
            val defaultText = buildAnnotatedString {
                withStyle(SpanStyle(color)) {
                    append("$label")
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
        //如果所在数字含有负号则不处理
        if (number.contains(label)) return

        if (cursorIndex == startIndex) {
            //光标位于数字开头,加负号
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