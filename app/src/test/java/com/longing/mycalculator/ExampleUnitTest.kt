package com.longing.mycalculator

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun calculator() {
        val expression = "-2%*3+4/2+(-2+1)"
        val result = Computer.performCalculate(expression)
        println("计算结果->$result")
    }
    @Test
    fun enum(){
        val values=OperatorType.values().map { it.label }
        println("全部运算符->$values")
    }
}