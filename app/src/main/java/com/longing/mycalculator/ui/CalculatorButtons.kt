package com.longing.mycalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.longing.mycalculator.*
import com.longing.mycalculator.model.Button
import com.longing.mycalculator.ui.theme.LightBlack
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.Red

private val buttonFontFamily = FontFamily(
    Font(R.font.roboto_regular)
)

@Composable
fun NumberButton(value: Int, modifier: Modifier) {
    val label = value.toString()
    val data = LocalCalculateData.current

    BasicCalculatorButton(
        modifier = modifier,
        label = label,
        fontSize = 28.sp,
        color = Color.White,
    ) {
        val expression = Computer.divideExpression(data.inputText)
        val currentExpression = buildAnnotatedString {
            if (data.inputText.text.isDigitsOnly()) {
                //置为白色样式
                withStyle(style = SpanStyle(Color.White)) {
                    append(expression.first)
                    append(label)
                    append(expression.second)
                }
            } else {
                //是个表达式,使用原来的输入样式
                append(expression.first)
                withStyle(style = SpanStyle(Color.White)) {
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

@Composable
fun OperationButton(type: OperatorType, modifier: Modifier) {
    val label = type.label.toString()
    val data = LocalCalculateData.current
    BasicCalculatorButton(
        modifier = modifier,
        label = label,
        fontSize = 34.sp,
        color = LightGreen
    ) {
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
            withStyle(style = SpanStyle(LightGreen)) {
                append(label)
            }
            append(right)
        }

        data.outputText = ""
        data.inputText =
            TextFieldValue(newExpression, TextRange(left.length + 1))
    }
}

@Composable
fun EqualButton(modifier: Modifier) {
    val data = LocalCalculateData.current

    BasicCalculatorButton(
        modifier = modifier,
        label = "=",
        fontSize = 36.sp,
        color = Color.White,
        backgroundColor = LightGreen
    ) {
        data.outputText = ""
        val result =
            Computer.performCalculate(data.inputText.text).let { str ->
                buildAnnotatedString {
                    withStyle(style = SpanStyle(LightGreen)) {
                        append(str)
                    }
                }
            }

        data.inputText = TextFieldValue(result, TextRange(result.length))
    }

}

@Composable
fun ClearButton(modifier: Modifier) {
    val data = LocalCalculateData.current
    BasicCalculatorButton(modifier = modifier, label = "C", fontSize = 26.sp, color = Red) {
        data.inputText = TextFieldValue()
        data.outputText = ""
    }
}

@Composable
fun BRACKET(modifier: Modifier) {
    // TODO:  优先级运算
}

@Composable
fun PercentButton(modifier: Modifier) {
    // TODO:  变小数
}

@Composable
fun BracketButton(modifier: Modifier) {
    // TODO: 优先级
}


@Composable
fun NegativeButton(modifier: Modifier) {
    // TODO: 负数
}


@Composable
private fun BasicCalculatorButton(
    modifier: Modifier,
    label: String,
    fontSize: TextUnit,
    color: Color,
    backgroundColor: Color = LightBlack,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(modifier)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = fontSize,
            color = color,
            textAlign = TextAlign.Center,
            fontFamily = buttonFontFamily
        )
    }
}
