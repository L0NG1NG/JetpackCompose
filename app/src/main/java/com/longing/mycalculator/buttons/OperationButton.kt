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
import com.longing.mycalculator.ui.theme.LightGreen

class OperationButton(type: OperatorType) : Button(type.label.toString(), 36.sp, LightGreen) {
    override fun onClick(data: CalculateData) {
        if (data.inputText.text.isBlank()) {
            return
        }
        val expression = Computer.divideExpression(data.inputText)
        var left = expression.first
        var right = expression.second
        val newExpression = buildAnnotatedString {
            if (OperatorType.values().any { expression.first.endsWith(it.label) }) {
                left = expression.first.subSequence(0, left.length - 1)
            }
            if (OperatorType.values().any { right.startsWith(it.label) }) {
                right = right.subSequence(1, right.length)
            }
            append(left)
            withStyle(style = SpanStyle(color)) {
                append(label)
            }
            append(right)
        }

        data.outputText = ""
        data.inputText =
            TextFieldValue(newExpression, TextRange(left.length + 1))
    }
}