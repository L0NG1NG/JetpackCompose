package com.longing.bloom.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.longing.bloom.ui.theme.BloomTheme
import com.longing.bloom.ui.theme.Shapes
import com.longing.bloom.ui.theme.body1
import com.longing.bloom.ui.theme.body2
import com.longing.bloom.ui.theme.button
import com.longing.bloom.ui.theme.gray
import com.longing.bloom.ui.theme.h1
import com.longing.bloom.ui.theme.pink900
import com.longing.bloom.ui.theme.white

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(white)
            .padding(horizontal = 16.dp)
    ) {
        LoginHeader()
        LoginInputBox()
        TermsOfServiceLabel()
        LoginButton(navController)

    }

}

@Composable
fun LoginHeader() {
    Text(
        text = "Log in with email",
        style = h1,
        color = gray,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .paddingFromBaseline(top = 184.dp, bottom = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun LoginInputBox() {
    LoginTextField("Email address")
    Spacer(modifier = Modifier.height(8.dp))
    LoginTextField("Password(8+Characters)")
}


@Composable
fun TermsOfServiceLabel() {
    val label = buildAnnotatedString {
        append("By clicking below you agree to our ")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Terms of Use")
        }
        append(" and consent to our ")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Privacy Policy")
        }
        append(".")
    }
    Text(
        text = label,
        style = body2,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .paddingFromBaseline(top = 24.dp, bottom = 16.dp),
    )


}


@Composable
fun LoginButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("home") {
                popUpTo("welcome") { inclusive = true }
            }
        },
        shape = Shapes.medium,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = pink900)
    ) {
        Text(text = "Log in", style = button, color = white)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(placeHolder: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(Shapes.small),
        placeholder = {
            Text(text = placeHolder, style = body1, color = gray)
        }
    )

}

@Preview
@Composable
fun LoginScreenPrev() {
    BloomTheme {
        LoginScreen(rememberNavController())
    }

}
