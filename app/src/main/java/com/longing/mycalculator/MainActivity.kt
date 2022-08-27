package com.longing.mycalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.longing.mycalculator.ui.ButtonPanel
import com.longing.mycalculator.ui.theme.Background
import com.longing.mycalculator.ui.theme.CyanBlue
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.MyCalculatorTheme

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

val robotFontFamily = FontFamily(
    Font(R.font.roboto_light)
)

val textSelectionColors = TextSelectionColors(
    handleColor = CyanBlue,
    backgroundColor = LightGreen.copy(alpha = 0.3f)
)

@Composable
fun Calculator() {
    val orientation = LocalConfiguration.current.orientation
    //remember保证下次重组时数据不进行改变
    val calculatorData = remember { ScreenData() }

    val focusRequester = remember { FocusRequester() }
    Column(
        Modifier
            .background(Background)
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {

            CompositionLocalProvider(
                LocalTextInputService provides null,
                LocalTextSelectionColors provides textSelectionColors,
            ) {
                //TODO 光标高度太高了 不会调
                BasicTextField(
                    value = calculatorData.inputText,
                    onValueChange = {
                        calculatorData.inputText = it
                    },
                    textStyle = TextStyle(
                        lineHeight = 38.sp,
                        fontSize = 46.sp,
                        fontFamily = robotFontFamily,
                        textAlign = TextAlign.End
                    ),
                    maxLines = 3,
                    cursorBrush = SolidColor(CyanBlue),
                    modifier = Modifier
                        .focusRequester(focusRequester),
                )
            }
            Text(
                text = calculatorData.outputText, fontSize = 24.sp,
                fontFamily = robotFontFamily,
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 1

            )
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(Modifier.height(56.dp))
        }
        Box {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ButtonPanel(calculatorData)
            } else {
                Box {
                    //todo:横屏
                    Text("TODO")
                }
            }
        }
        LaunchedEffect(Unit) {
            //默认让textFiled有光标在闪
            focusRequester.requestFocus()
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyCalculatorTheme {
        Calculator()
    }
}

