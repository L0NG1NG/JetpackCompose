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

class PercentButton : Button(label, fontSize = 26.sp, color = LightGreen) {
    override fun onClick(data: CalculateData) {
        val inputText = data.inputText
        if (inputText.text.isBlank()) {
            return
        }
        val expression = Computer.divideExpression(inputText)
        val left = expression.first
        val right = expression.second
        
        if (left.endsWith(label) || right.startsWith(label)
            || OperatorType.values().any { left.endsWith(it.label) }
        ) {
            return
        }

        val newExpression = buildAnnotatedString {
            append(left)
            withStyle(SpanStyle(color)) {
                append(label)
            }
            append(right)
        }
        data.inputText =
            TextFieldValue(newExpression, TextRange(left.length + 1))
        // TODO: 除了末尾,百分号后面有运算符隔开才进行运算 
        data.outputText =
            Computer.performCalculate(newExpression.text)

    }

    companion object {
        const val label = "%"
    }
}