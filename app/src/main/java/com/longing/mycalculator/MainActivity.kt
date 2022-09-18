package com.longing.mycalculator

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.longing.mycalculator.buttons.*
import com.longing.mycalculator.ui.ButtonPanel
import com.longing.mycalculator.ui.DisplayScreen
import com.longing.mycalculator.ui.theme.Background
import com.longing.mycalculator.ui.theme.LightGreen
import com.longing.mycalculator.ui.theme.MyCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //transparent status bar
        window.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
            statusBarColor = Color.Transparent.value.toInt()
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        val viewModel = ViewModelProvider(this)[CalculateViewModel::class.java]

        setContent {
            MyCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    CompositionLocalProvider(
                        LocalCalculateData provides viewModel.calculateData
                    ) {
                        Calculator(viewModel.buttons)
                    }
                }
            }
        }
    }
}

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
            DisplayScreen(Modifier.weight(1f))
            Spacer(Modifier.height(16.dp))
            MenuItems()
            Box(
                Modifier
                    .padding(8.dp)
                    .height(32.dp)
            ) {
                Spacer(
                    Modifier
                        .height(0.4.dp)
                        .padding(start = 6.dp, end = 6.dp)
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

