package com.longing.mycalculator

import androidx.lifecycle.ViewModel
import com.longing.mycalculator.buttons.*

class CalculateViewModel : ViewModel() {
    val buttons = listOf(
        ClearButton(),
        BracketButton(),
        PercentButton(),
        OperationButton(OperatorType.DIVIDE),

        NumberButton(7),
        NumberButton(8),
        NumberButton(9),
        OperationButton(OperatorType.MULTIPLY),

        NumberButton(4),
        NumberButton(5),
        NumberButton(6),
        OperationButton(OperatorType.SUBTRACT),

        NumberButton(1),
        NumberButton(2),
        NumberButton(3),
        OperationButton(OperatorType.PLUS),

        NegativeButton(),
        NumberButton(0),
        DotButton(),
        EqualButton()
    )

    val calculateData = CalculateData()
}