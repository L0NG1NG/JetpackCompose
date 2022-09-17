package com.longing.mycalculator

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.longing.mycalculator.buttons.PercentButton
import com.longing.mycalculator.exprk.Expressions
import java.math.RoundingMode

enum class OperatorType(val label: Char) {
    PLUS('+'), SUBTRACT('–'), MULTIPLY('×'), DIVIDE('÷'),
}

object Computer {
    private const val TAG = "Computer"

    private  val operators = arrayOf('+', '-', '*', '/')

    fun performCalculate(expressions: String): String {
        val finalExpression = expressions
            .replace(OperatorType.MULTIPLY.label, '*')
            .replace(OperatorType.DIVIDE.label, '/')
            .replace(OperatorType.SUBTRACT.label,'-')
            .replace(PercentButton.label, "/100")//简单粗暴

        if (!operators.any { finalExpression.contains(it) }) {
            return ""
        }
        if (operators.any { finalExpression.endsWith(it) }) {
            return ""
        }
        val eval = Expressions().eval(finalExpression)

        return if (eval.toString().contains(".")) {
            val rounded = eval.setScale(3, RoundingMode.UP).stripTrailingZeros()
            rounded.toString()
        } else {
            eval.toString()
        }
    }

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