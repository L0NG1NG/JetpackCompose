package com.longing.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.longing.mycalculator.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //transparent status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.value.toInt()

        setContent {
            MyCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Calculator()
                }
            }
        }
    }

}


data class CalculateState(val num1: Int = 0, val num2: Int = 0, val opt: String? = null)

val buttons = arrayOf(
    arrayOf("C" to LightGray, "+/-" to LightGray, "%" to LightGray, "/" to Orange),
    arrayOf("7" to DarkGray, "8" to DarkGray, "9" to DarkGray, "*" to Orange),
    arrayOf("4" to DarkGray, "5" to DarkGray, "6" to DarkGray, "-" to Orange),
    arrayOf("1" to DarkGray, "2" to DarkGray, "3" to DarkGray, "+" to Orange),
    arrayOf("0" to DarkGray, "." to DarkGray, "=" to Orange),

    )

@Composable
fun Calculator() {
    //remember保证下次重组时数据不进行改变
    var state by remember {
        mutableStateOf(CalculateState())
    }
    Column(
        Modifier
            .background(Background)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd,
        ) {

            Text(
                text = state.num2.toString(), fontSize = 100.sp,
                color = Color.White
            )
        }
        Spacer(Modifier.height(48.dp))
        Column(Modifier.fillMaxSize()) {
            buttons.forEach {
                Row(
                    Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    it.forEach { symbol ->
                        CalculatorButton(
                            Modifier
                                .background(symbol.second)
                                .weight(if (symbol.first == "0") 2f else 1f)
                                .aspectRatio(if (symbol.first == "0") 2f else 1f),
                            symbol.first
                        ) {
                            state = calculate(state, symbol.first)
                        }
                    }

                }
            }
        }
    }
}

fun calculate(curState: CalculateState, input: String): CalculateState {
    return when (input) {
        in "0".."9" -> curState.copy(num2 = input.toInt(), num1 = curState.num2)
        in arrayOf("+", "-", "*", "/", "%") -> curState.copy(opt = input)
        "=" -> when (curState.opt) {
            "+" -> curState.copy(num2 = curState.num1 + curState.num2)
            "-" -> curState.copy(num2 = curState.num1 - curState.num2)
            "*" -> curState.copy(num2 = curState.num1 * curState.num2)
            "/" -> curState.copy(num2 = curState.num1 / curState.num2)
            "%" -> curState.copy(num2 = curState.num1 % curState.num2)
            else -> curState
        }

        "C" -> curState.copy(num1 = 0, num2 = 0, opt = null)
        else -> curState
    }
}

@Composable
fun CalculatorButton(modifier: Modifier, symbol: String, onClick: () -> Unit = {}) {
    Box(
        Modifier
            .clip(CircleShape)
            .then(modifier)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, fontSize = 40.sp, color = Color.White)
    }
}
