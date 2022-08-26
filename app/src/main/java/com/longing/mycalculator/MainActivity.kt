package com.longing.mycalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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

val robotFontFamily = FontFamily(
    Font(R.font.roboto_light)
)

val textSelectionColors = TextSelectionColors(
    handleColor = LightGreen,
    backgroundColor = LightGreen.copy(alpha = 0.4f)
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
            .padding(10.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            CompositionLocalProvider(
                LocalTextInputService provides null,
                LocalTextSelectionColors provides textSelectionColors,
            ) {
                TextField(
                    value = calculatorData.inputText,
                    textStyle = TextStyle(
                        fontSize = 46.sp,
                        fontFamily = robotFontFamily,
                        textAlign = TextAlign.End
                    ),
                    onValueChange = { calculatorData.inputText = it },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = LightGreen,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )
            }
            Box(Modifier.padding(end = 16.dp)) {
                Text(
                    text = calculatorData.outputText, fontSize = 24.sp,
                    fontFamily = robotFontFamily,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }

        }
        Spacer(Modifier.height(48.dp))
        Box(Modifier.fillMaxSize()) {
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

