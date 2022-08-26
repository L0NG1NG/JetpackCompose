package com.longing.mycalculator

import com.longing.mycalculator.exprk.Expressions
import java.math.RoundingMode


object Computer {
    enum class Operator(val label: Char) {
        PLUS('+'), SUBTRACT('-'), MULTIPLY('×'), DIVIDE('÷'),
    }
    private val operators = arrayOf('+', '-', '*', '/')

    fun performCalculate(expressions: String): String {
        val finalExpression = expressions
            .replace(Operator.MULTIPLY.label, '*')
            .replace(Operator.DIVIDE.label, '/')

        if (!operators.any { finalExpression.contains(it) }) {
            return ""
        }
        if (operators.any { finalExpression.endsWith(it) }) {
            return ""
        }
        val eval = Expressions().eval(finalExpression)

        return if (eval.toString().contains(".")) {
            val rounded = eval.setScale(10, RoundingMode.UP).stripTrailingZeros()
            rounded.toString()
        } else {
            eval.toString()
        }
    }
}