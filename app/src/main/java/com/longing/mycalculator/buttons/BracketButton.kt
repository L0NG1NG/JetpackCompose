package com.longing.mycalculator.buttons

import androidx.compose.ui.unit.sp
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.ui.theme.LightGreen

class BracketButton:Button("( )", fontSize = 22.sp, color = LightGreen) {
    override fun onClick(data: CalculateData) {

    }
}