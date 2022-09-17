package com.longing.mycalculator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.longing.mycalculator.LocalCalculateData
import com.longing.mycalculator.buttons.Button


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonPanel(buttons: List<Button>, cells: Int = 4) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(cells),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buttons.size) { item ->
            CalculatorButtonItem(buttons[item], Modifier.aspectRatio(1f))
        }
    }
}

@Composable
private fun CalculatorButtonItem(button: Button, modifier: Modifier) {
    val data = LocalCalculateData.current
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(button.backgroundColor)
            .then(modifier)
            .clickable { button.onClick(data) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = button.label,
            fontSize = button.fontSize,
            color = button.color,
            textAlign = TextAlign.Center,
            fontFamily = button.fontFamily
        )
    }
}
