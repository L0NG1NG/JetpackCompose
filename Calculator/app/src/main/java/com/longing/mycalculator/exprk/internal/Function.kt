package com.longing.mycalculator.exprk.internal

import java.math.BigDecimal

abstract class Function {

    abstract fun call(arguments: List<BigDecimal>): BigDecimal

}