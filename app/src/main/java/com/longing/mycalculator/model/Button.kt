package com.longing.mycalculator.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
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

    open class Number(value: String) : Button(value) {
        override fun onClick(data: ScreenData): ScreenData {
            val currentExpression = data.inputText.text.plus(text)
            data.outputText =
                Computer.performCalculate(currentExpression)
            data.inputText =
                TextFieldValue(
                    text = currentExpression,
                    selection = TextRange(currentExpression.length)
                )
            return data
        }
    }

    class Delete : Button("C", fontSize = 26.sp, fontColor = Red) {
        override fun onClick(data: ScreenData): ScreenData {
            data.inputText = TextFieldValue()
            return data
        }
    }

    class Operator(type: Computer.Operator) :
        Button(type.label.toString(), fontColor = LightGreen, fontSize = 34.sp) {
        override fun onClick(data: ScreenData): ScreenData {
            if (operators.any { data.inputText.text.endsWith(it) }) {
                val newExpression = data.inputText.text.dropLast(1).plus(text)
                data.inputText = TextFieldValue(newExpression, TextRange(newExpression.length))
            } else {
                val currentExpression = data.inputText.text.plus(text)
                data.outputText =
                    Computer.performCalculate(currentExpression)
                data.inputText =
                    TextFieldValue(currentExpression, TextRange(currentExpression.length))
            }
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
                Computer.performCalculate(data.inputText.text)
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

    class Negative : Button("+/-", fontSize = 22.sp) {
        //todo 负数
    }
}



