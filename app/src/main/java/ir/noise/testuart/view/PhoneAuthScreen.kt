package ir.noise.testuart.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.noise.testuart.viewmodel.PhoneAuthViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PhoneAuthScreen(viewModel: PhoneAuthViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.onBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.phoneNumber,
            onValueChange = {},
            label = { Text("Enter Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomNumericKeyboard(
            onDigitClick = { viewModel.onAddDigit(it) },
            onDeleteClick = { viewModel.onDeleteDigit() },
            onSubmitClick = { viewModel.sendPhoneNumberToServer() },
            isSubmitting = uiState is PhoneAuthViewModel.UiState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is PhoneAuthViewModel.UiState.Success -> Text(
                text = "Success: ${(uiState as PhoneAuthViewModel.UiState.Success).message}",
                color = MaterialTheme.colorScheme.primary
            )
            is PhoneAuthViewModel.UiState.Error -> Text(
                text = "Error: ${(uiState as PhoneAuthViewModel.UiState.Error).error}",
                color = MaterialTheme.colorScheme.error
            )
            is PhoneAuthViewModel.UiState.Loading -> CircularProgressIndicator()
            PhoneAuthViewModel.UiState.Idle -> {}
        }

    }
}

@Composable
fun CustomNumericKeyboard(
    onDigitClick: (Char) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    isSubmitting: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        // دکمه‌های اعداد
        (1..9).forEach { digit ->
            item {
                Button(
                    onClick = { onDigitClick(digit.toString().first()) },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(digit.toString())
                }
            }
        }

        // دکمه Delete
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .clickable {
                        onDeleteClick()
                    }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        // دکمه 0
        item {
            Button(
                onClick = { onDigitClick('0') },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("0")
            }
        }

        // دکمه Send
        item {
            Button(
                onClick = { onSubmitClick() },
                modifier = Modifier.padding(4.dp),
                enabled = !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Send")
                }
            }
        }
    }
}
