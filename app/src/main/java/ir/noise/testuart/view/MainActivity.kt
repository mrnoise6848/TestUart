package ir.noise.testuart.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.noise.testuart.ui.theme.TestUartTheme
import ir.noise.testuart.viewmodel.PhoneAuthViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestUartTheme {
                val viewModel: PhoneAuthViewModel = hiltViewModel()
                PhoneAuthScreen(viewModel = viewModel)
            }
        }
    }
}


/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestUartTheme {
        Greeting("Android")
    }
}*/
