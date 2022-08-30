package com.longing.mycalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.longing.mycalculator.Computer
import com.longing.mycalculator.CalculateData
import com.longing.mycalculator.OperatorType
import com.longing.mycalculator.model.Button
import com.longing.mycalculator.ui.theme.MyCalculatorTheme

private val buttonColumns = listOf(
    listOf(
        Button.Delete(),
        Button.Number("7"),
        Button.Number("4"),
        Button.Number("1"),
        Button.Negative()
    ),
    listOf(
        Button.BRACKET(),
        Button.Number("8"),
        Button.Number("5"),
        Button.Number("2"),
        Button.Number("0")
    ),
    listOf(
        Button.Percent(),
        Button.Number("9"),
        Button.Number("6"),
        Button.Number("3"),
        Button.Number(".")
    ),

    listOf(
        Button.Operator(OperatorType.DIVIDE),
        Button.Operator(OperatorType.MULTIPLY),
        Button.Operator(OperatorType.SUBTRACT),
        Button.Operator(OperatorType.PLUS),
        Button.Equal()
    ),
)


@Composable
fun ButtonPanel(calculatorData: CalculateData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        buttonColumns.forEach { buttons ->
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                buttons.forEach { button ->
                    val modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)

                    with(button) {
                        CalculatorButton(
                            modifier,
                            text,
                            fontSize,
                            fontColor,
                            fontFamily,
                            backgroundColor
                        ) {
                            onClick(calculatorData)
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun MenuRow(screenData: ScreenData) {
//    Row {
//        Row {
//
//        }
//        IconButton(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth(),
//            onClick = {
//                if (screenData.inputText.text.isNotEmpty()) {
//                    inputText = TextFieldValue(
//                        text = inputText.text.substring(0, inputText.text.length - 1)
//                    )
//                }
//                performCalculation()
//            }) {
//            Icon(
//                asset = vectorResource(id = R.drawable.ic_outline_backspace_24),
//                tint = AppState.theme.primary
//            )
//        }
//    }
//
//}

@Preview
@Composable
fun ButtonPanelPreview() {
    MyCalculatorTheme {
        ButtonPanel(CalculateData())
    }
}

@Composable
private fun CalculatorButton(
    modifier: Modifier,
    label: String,
    fontSize: TextUnit,
    color: Color,
    fontFamily: FontFamily,
    backgroundColor: Color,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(modifier)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = fontSize,
            color = color,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily
        )
    }
}





