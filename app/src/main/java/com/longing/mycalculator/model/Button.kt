package com.longing.mycalculator.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.longing.mycalculator.Computer
import com.longing.mycalculator.ScreenData
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
private const val TAG = "Button"

sealed class Button(
    val text: String,
    val fontColor: Color = Color.White,
    val fontSize: TextUnit = 28.sp,
    val backgroundColor: Color = LightBlack,
    val fontFamily: FontFamily = FontFamily(
        Font(R.font.roboto_regular)
    )
) {

    open fun onClick(data: ScreenData): ScreenData {
        return data
    }

    companion object {
        // 分出表达式左右两边内容
        fun divideExpression(
            expression: TextFieldValue
        ): Pair<AnnotatedString, AnnotatedString> {
            return with(expression) {
                val cursorIndex = selection.start
                val left = annotatedString.subSequence(0, cursorIndex)
                val right = annotatedString.subSequence(
                    cursorIndex,
                    if (annotatedString.isNotEmpty()) annotatedString.length else 0
                )
                Log.d(TAG, "onClick: left->$left  right->$right")
                left to right
            }
        }
    }

    open class Number(value: String) : Button(value) {
        override fun onClick(data: ScreenData): ScreenData {
            val expression = divideExpression(data.inputText)

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
        override fun onClick(data: ScreenData): ScreenData {
            data.inputText = TextFieldValue()
            data.outputText = ""
            return data
        }
    }

    class Operator(type: Computer.Operator) :
        Button(type.label.toString(), fontColor = LightGreen, fontSize = 34.sp) {
        override fun onClick(data: ScreenData): ScreenData {
            val expression = divideExpression(data.inputText)
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
                Computer.Operator.PLUS.label,
                Computer.Operator.SUBTRACT.label,
                Computer.Operator.MULTIPLY.label,
                Computer.Operator.DIVIDE.label,
            )
        }
    }

    class Equal : Button("=", backgroundColor = LightGreen, fontSize = 36.sp) {
        override fun onClick(data: ScreenData): ScreenData {
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
        override fun onClick(data: ScreenData): ScreenData {
            //todo 优先级运算
            return super.onClick(data)
        }
    }

    class Percent : Button("%", fontSize = 26.sp, fontColor = LightGreen) {
        override fun onClick(data: ScreenData): ScreenData {
            //todo 小数运算
            return super.onClick(data)
        }
    }

    class Negative : Button("+/-", fontSize = 24.sp) {
        //todo 负数
    }
}


