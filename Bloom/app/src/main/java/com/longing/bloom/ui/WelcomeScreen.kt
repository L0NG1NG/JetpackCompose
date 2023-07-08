package com.longing.bloom.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.longing.bloom.R
import com.longing.bloom.ui.theme.BloomTheme
import com.longing.bloom.ui.theme.Shapes
import com.longing.bloom.ui.theme.button
import com.longing.bloom.ui.theme.gray
import com.longing.bloom.ui.theme.pink100
import com.longing.bloom.ui.theme.pink900
import com.longing.bloom.ui.theme.subtitle1
import com.longing.bloom.ui.theme.white

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pink100)
    ) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_welcome_bg)),
            contentDescription = "welcome bg",
            modifier = Modifier.fillMaxSize()
        )
        WelcomeContent(navController)
    }
}

@Composable
fun WelcomeContent(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(72.dp))
        LeafImage()
        Spacer(modifier = Modifier.height(48.dp))
        WelcomeTitle()
        Spacer(modifier = Modifier.height(40.dp))
        WelcomeButtons(navController)
    }

}

@Composable
fun WelcomeButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {

            },
            shape = Shapes.medium,
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = pink900)
        ) {
            Text(
                text = "Create account",
                style = button,
                color = white
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(
                text = "Log in",
                style = button,
                color = pink900
            )
        }

    }

}

@Composable
fun WelcomeTitle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_logo)),
            contentDescription = "logo",
            modifier = Modifier
                .wrapContentWidth()
                .height(32.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Beautiful home garden solutions",
                style = subtitle1,
                color = gray
            )

        }

    }

}

@Composable
fun LeafImage() {
    Image(
        painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_welcome_illos)),
        contentDescription = "welcome_illos",
        Modifier.offset(x = 88.dp)
    )
}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun WelcomeScreenPreview() {
    BloomTheme {
        WelcomeScreen(rememberNavController())
    }
}