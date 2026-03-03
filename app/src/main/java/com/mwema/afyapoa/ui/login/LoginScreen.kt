package com.mwema.afyapoa.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mwema.afyapoa.AfyaPoa
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.CurvedHeader
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.UIEvent
import com.mwema.afyapoa.utils.factory.LoginViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    val context = (LocalContext.current.applicationContext as AfyaPoa)
    val apiImplementation = remember {
        mutableStateOf(ApiImplementation(context.api, context.tokenManager))
    }
    val loginViewModel = viewModel<LoginScreenViewModel>(
        factory = LoginViewModelFactory(apiImplementation.value)
    )
    val uiState = loginViewModel.uiState
    val registrationNumber: String = loginViewModel.registrationNumber
    val password: String = loginViewModel.password
    var error by remember {
        mutableStateOf<Map<String, String>>(emptyMap())
    }
    val snackBarHostState = remember { SnackbarHostState() }

    uiState.value.error?.let {
        LaunchedEffect(it) {
            snackBarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(key1 = true) {
        loginViewModel.uiChannel.collect { event ->
            when (event) {
                is UIEvent.Navigate -> onNavigate(event.route)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CurvedHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight(0.4f),
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = "LOGIN"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = registrationNumber,
                        onValueChange = {
                            loginViewModel.updateRegistrationNumber(it).also {
                                error = validate(registrationNumber, password)
                            }
                        },
                        placeholder = {
                            Text(text = "Enter your Registration Number")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        isError = error.containsKey("registrationNumber"),
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorBorderColor = Color.Red,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            loginViewModel.updatePassword(it).also {
                                error = validate(registrationNumber, password)
                            }
                        },
                        placeholder = {
                            Text(text = "Enter your Password")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        isError = error.containsKey("password"),
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorBorderColor = Color.Red,
                        )
                    )
                    Spacer(Modifier.height(30.dp))
                    Button(
                        onClick = {
                            loginViewModel.executeUIEvent(LoginScreenEvent.Login)
                        },
                        enabled = !uiState.value.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Login",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Don\'t Have an Account?")
                            append(" ")
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            ) { append("SignUP") }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            loginViewModel.executeUIEvent(LoginScreenEvent.NavigateToSignUp(Routes.SIGNUP))
                        }
                    )

                    when {
                        uiState.value.isLoading -> CircularProgressIndicator()
                        uiState.value.success -> {
                            loginViewModel.executeUIEvent(LoginScreenEvent.NavigateToHome(Routes.MAINSCREEN))
                        }
                    }

                }
            }

        }
    }
}