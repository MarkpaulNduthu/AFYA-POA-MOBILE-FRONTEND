package com.mwema.afyapoa.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.utils.UIEvent
import com.mwema.afyapoa.utils.UIEvent.Navigate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val apiImplementation: ApiImplementation
) : ViewModel() {

    var registrationNumber by mutableStateOf("")

    var password by mutableStateOf("")


    private var _uiState = mutableStateOf(LoginUIState())
    val uiState: State<LoginUIState> = _uiState

    private val _uiChannel = Channel<UIEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun updateRegistrationNumber(registrationNumber: String) {
        this.registrationNumber = registrationNumber
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun executeUIEvent(loginScreenEvent: LoginScreenEvent) {
        when (loginScreenEvent) {
            is LoginScreenEvent.Login -> {
                viewModelScope.launch {
                    _uiState.value = LoginUIState(isLoading = true)
                    val authResponse = apiImplementation.login(
                        AuthenticationRequest(
                            registrationNumber,
                            password
                        )
                    )
                    if (authResponse.isSuccess
                    ) {
                        _uiState.value = LoginUIState(success = true)
                    } else {
                        _uiState.value =
                            LoginUIState(error = "Failed to Login, Please ensure you are connected to the internet and try again")
                    }
                }
            }
            is LoginScreenEvent.NavigateToSignUp -> {
                viewModelScope.launch {
                    _uiChannel.send(Navigate(loginScreenEvent.route))
                }
            }
            is LoginScreenEvent.NavigateToHome -> {
                viewModelScope.launch {
                    _uiChannel.send(Navigate(loginScreenEvent.route))
                }
            }
        }
    }
}