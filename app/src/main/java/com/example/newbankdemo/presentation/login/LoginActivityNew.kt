package com.example.newbankdemo.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.presentation.login.ui.theme.NewBankDemoTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginActivityNew : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewBankDemoTheme {
                val loginViewModel: LoginViewModel = getViewModel()
                LoginScreen(loginViewModel = loginViewModel)
            }
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    //  val loadingState = loginViewModel.loginFormState.collectAsState()
    // Observe the login form state
    //val loadingState by loginViewModel.loginFormState.collectAsState()

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        // Handle done action
                        loginViewModel.login(usernameState.value, passwordState.value)
                    })
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { loginViewModel.login(usernameState.value, passwordState.value) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))
                val loginFormState by loginViewModel.login.observeAsState()

                loginFormState?.let { result ->
                    when (result) {
                        is Output.Success -> {
                            Log.d("Succcess","Success")
                        }
                        is Output.Error -> {
                            Log.d("Error","Er")
                        }
                        is Output.Loading -> {
                            // Handle loading state if needed
                        }
                    }
                }

            }
        }
    )
}