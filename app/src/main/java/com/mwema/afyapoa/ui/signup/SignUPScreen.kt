package com.mwema.afyapoa.ui.signup

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.mwema.afyapoa.ui.login.validate
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.UIEvent
import com.mwema.afyapoa.utils.factory.SignUpViewModelFactory
import kotlin.math.sign

@Composable
fun SignUPScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current.applicationContext as AfyaPoa
    val signUPViewModel = viewModel<SignUPViewModel>(
        factory = SignUpViewModelFactory(ApiImplementation(context.api, context.tokenManager))
    )
    val registrationNumber = signUPViewModel.registrationNumber
    val password = signUPViewModel.password
    val email = signUPViewModel.email
    var error = signUPViewModel.error
    val uiState by signUPViewModel.screenState


    LaunchedEffect(key1 = true) {
        signUPViewModel.uiChannel.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    onNavigate(event.route)
                }
            }
        }
    }


    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CurvedHeader(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.primary,
            title = "SIGNUP"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        signUPViewModel.updateEmail(it)
//                            .also {
//                            signUPViewModel.updateError(validate(registrationNumber, password))
//                        }
                    },
                    placeholder = {
                        Text(text = "Enter your Email")
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
//                    isError = error.containsKey("registrationNumber"),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        errorTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        errorBorderColor= Color.Red,
                    )
                )
                OutlinedTextField(
                    value = registrationNumber,
                    onValueChange = {
                        signUPViewModel.updateRegistrationNumber(it).also {
                            signUPViewModel.updateError(validate(registrationNumber, password))
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
                        errorBorderColor= Color.Red,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        signUPViewModel.updatePassword(it).also {
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
                        errorBorderColor= Color.Red,
                    )
                )
                Spacer(Modifier.height(30.dp))
                Button(
                    onClick = {
                        signUPViewModel.executeUIEvent(SignUpScreenEvent.SignUP)
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "SignUP",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                Spacer(Modifier.height(30.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Already Have an Account?")
                        append(" ")
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) { append("Login") }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        signUPViewModel.executeUIEvent(SignUpScreenEvent.NavigateToLogin(Routes.LOGIN))
                    }
                )
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.error != null -> {

                        Text(
                            text = "Something went wrong! ${uiState.error}"
                        )

                    }

                    uiState.success -> {
                        signUPViewModel.executeUIEvent(SignUpScreenEvent.NavigateToLogin(Routes.LOGIN))
                    }
                }

            }
        }
    }

}