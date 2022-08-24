package com.longing.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.longing.mycalculator.exprk.Expressions
import com.longing.mycalculator.ui.theme.*
import java.math.RoundingMode

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


val buttons = arrayOf(
    arrayOf("C" to LightGray, "+/-" to LightGray, "%" to LightGray, "/" to Orange),
    arrayOf("7" to DarkGray, "8" to DarkGray, "9" to DarkGray, "*" to Orange),
    arrayOf("4" to DarkGray, "5" to DarkGray, "6" to DarkGray, "-" to Orange),
    arrayOf("1" to DarkGray, "2" to DarkGray, "3" to DarkGray, "+" to Orange),
    arrayOf("0" to DarkGray, "." to DarkGray, "=" to Orange),

    )
val robotFontFamily = FontFamily(
    Font(R.font.roboto_light)
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Calculator() {
    //remember保证下次重组时数据不进行改变
    var inputText by remember {
        mutableStateOf(TextFieldValue(text = ""))
    }

    var result by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .background(Background)
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {

            TextField(
                value = inputText,
                textStyle = TextStyle(
                    fontSize = 46.sp,
                    fontFamily = robotFontFamily,
                    textAlign = TextAlign.End
                ),
                onValueChange = { inputText = it },
                modifier = Modifier.onFocusEvent { keyboardController?.hide() },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = result, fontSize = 24.sp,
                fontFamily = robotFontFamily,
                color = Color.White,
                textAlign = TextAlign.End
            )

        }

        Spacer(Modifier.height(48.dp))
        Column(Modifier.fillMaxSize()) {
            buttons.forEach {
                Row(
                    Modifier.weight(1f),
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
                            when (symbol.first) {
                                "=" -> {
                                    result = ""
                                    inputText = TextFieldValue(performCalculate(inputText.text))

                                }
                                "C" -> {
                                    result = ""
                                    inputText = TextFieldValue("")

                                }
                                else -> {
                                    result = performCalculate(inputText.text)
                                    inputText = TextFieldValue(text = inputText.text + symbol.first)

                                }
                            }
                        }
                    }
                }
            }
        }
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

@Preview
@Composable
fun DefaultPreview() {
    MyCalculatorTheme {
        Calculator()
    }
}


fun performCalculate(expressions: String): String {
    val operators = arrayOf('+', '-', '*', '/')
    if (!operators.any { expressions.contains(it) }) {
        return ""
    }
    if (operators.any { expressions.endsWith(it) }) {
        return ""
    }
    val eval = Expressions().eval(expressions)

    return if (eval.toString().contains(".")) {
        val rounded = eval.setScale(10, RoundingMode.UP).stripTrailingZeros()
        rounded.toString()
    } else {
        eval.toString()
    }
}
