package com.longing.mycalculator.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.longing.mycalculator.Computer
import com.longing.mycalculator.OperatorType
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.R
import com.longing.mycalculator.ui.theme.LightBlack
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.Red

/**
 *
 * 按钮的属性样式
 * (有现成的图片就好了)
 *
 */
sealed class Button(
    val text: String,
    val fontColor: Color = Color.White,
    val fontSize: TextUnit = 28.sp,
    val backgroundColor: Color = LightBlack,
    val fontFamily: FontFamily = FontFamily(
        Font(R.font.roboto_regular)
    )
) {

    open fun onClick(data: CalculateData): CalculateData {
        return data
    }


    open class Number(value: String) : Button(value) {
        override fun onClick(data: CalculateData): CalculateData {
            val expression = Computer.divideExpression(data.inputText)

            val currentExpression = buildAnnotatedString {
                if (data.inputText.text.isDigitsOnly()) {
                    //置为白色样式
                    withStyle(style = SpanStyle(Color.White)) {
                        append(expression.first)
                        append(text)
                        append(expression.second)
                    }
                } else {
                    //是个表达式,使用原来的输入样式
                    append(expression.first)
                    withStyle(style = SpanStyle(Color.White)) {
                        append(text)
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
            return data
        }
    }

    class Delete : Button("C", fontSize = 26.sp, fontColor = Red) {
        override fun onClick(data: CalculateData): CalculateData {
            data.inputText = TextFieldValue()
            data.outputText = ""
            return data
        }
    }

    class Operator(type: OperatorType) :
        Button(type.label.toString(), fontColor = LightGreen, fontSize = 34.sp) {
        override fun onClick(data: CalculateData): CalculateData {
            val expression = Computer.divideExpression(data.inputText)
            var left = expression.first
            var right = expression.second
            val newExpression = buildAnnotatedString {
                if (operators.any { expression.first.endsWith(it) }) {
                    left = expression.first.subSequence(0, left.length - 1)
                }
                if (operators.any { right.startsWith(it) }) {
                    right = right.subSequence(1, right.length)
                }
                append(left)
                withStyle(style = SpanStyle(LightGreen)) {
                    append(text)
                }
                append(right)
            }

            data.outputText = ""
            data.inputText =
                TextFieldValue(newExpression, TextRange(left.length + 1))
            return data
        }


        companion object {
            val operators = arrayOf(
                OperatorType.PLUS.label,
                OperatorType.SUBTRACT.label,
                OperatorType.MULTIPLY.label,
                OperatorType.DIVIDE.label,
            )
        }
    }

    class Equal : Button("=", backgroundColor = LightGreen, fontSize = 36.sp) {
        override fun onClick(data: CalculateData): CalculateData {
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
            return data
        }
    }

    class BRACKET : Button("( )", fontSize = 22.sp, fontColor = LightGreen) {
        override fun onClick(data: CalculateData): CalculateData {
            //todo 优先级运算
            return super.onClick(data)
        }
    }

    class Percent : Button("%", fontSize = 26.sp, fontColor = LightGreen) {
        override fun onClick(data: CalculateData): CalculateData {
            //todo 小数运算
            return super.onClick(data)
        }
    }

    class Negative : Button("+/-", fontSize = 24.sp) {
        //todo 负数
    }
}


