package com.longing.mycalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.longing.mycalculator.buttons.*
import com.longing.mycalculator.ui.ButtonPanel
import com.longing.mycalculator.ui.DisplayScreen
import com.longing.mycalculator.ui.theme.Background
import com.longing.mycalculator.ui.theme.CyanBlue
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.MyCalculatorTheme

class MainActivity : ComponentActivity() {

    private val buttons = listOf(
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
    private val calculateData = CalculateData()

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
                    CompositionLocalProvider(
                        LocalCalculateData provides calculateData
                    ) {
                        Calculator(buttons)
                    }
                }
            }
        }
    }
}


val robotFontFamily = FontFamily(
    Font(R.font.roboto_light)
)

val textSelectionColors = TextSelectionColors(
    handleColor = CyanBlue,
    backgroundColor = LightGreen.copy(alpha = 0.3f)
)


@Composable
fun Calculator(buttons: List<Button>) {
    val orientation = LocalConfiguration.current.orientation
    Column(
        Modifier
            .background(Background)
            .fillMaxSize()
            .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            DisplayScreen()
            MenuItems()
            Box(
                Modifier
                    .padding(8.dp)
                    .height(32.dp)
            ) {
                Spacer(
                    Modifier
                        .height(0.8.dp)
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                        .background(Color.Gray)
                )
            }
            ButtonPanel(buttons)
        } else {
            Box {
                //todo:横屏
                Text("TODO")
            }
        }
    }
}

@Composable
fun MenuItems() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        val calculateData = LocalCalculateData.current
        Spacer(modifier = Modifier.weight(0.8f))
        IconButton(modifier = Modifier.weight(0.2f),
            onClick = {
                val expression = Computer.divideExpression(calculateData.inputText)
                var left = expression.first
                if (left.isNotEmpty()) {
                    left = left.subSequence(0, left.length - 1)
                    val newExpression = buildAnnotatedString {
                        append(left)
                        append(expression.second)
                    }
                    calculateData.inputText = TextFieldValue(
                        annotatedString = newExpression,
                        TextRange(left.length)
                    )
                    Computer.performCalculate(calculateData.inputText.text)
                }
            }) {
            Icon(
                painter = painterResource(R.drawable.ic_outline_backspace_24),
                "删除",
                tint = LightGreen
            )
        }
    }
}

