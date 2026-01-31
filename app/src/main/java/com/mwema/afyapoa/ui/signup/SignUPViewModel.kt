package com.mwema.afyapoa.ui.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.UIEvent
import com.mwema.afyapoa.utils.UIEvent.Navigate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUPViewModel(
    private val apiImplementation: ApiImplementation
) : ViewModel() {

    var registrationNumber by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var error by mutableStateOf<Map<String, String>>(emptyMap())
        private set
    private val _uiChannel = Channel<UIEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()
    private var _screenState = mutableStateOf(SignUpUIState())
    val screenState: State<SignUpUIState> = _screenState
    fun updateRegistrationNumber(registrationNumber: String) {
        this.registrationNumber = registrationNumber
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun updateError(error: Map<String, String>) {
        this.error = error
    }

    fun executeUIEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.SignUP -> {
                viewModelScope.launch {
                    try {
                        _screenState.value = SignUpUIState(isLoading = true)
                        val result = apiImplementation.register(
                            AuthenticationRequest(
                                registrationNumber,
                                password
                            )
                        )
                        if (result.isSuccess) {
                            _screenState.value = SignUpUIState(success = true)
                            _uiChannel.send(Navigate(Routes.LOGIN))
                        }else{
                            throw Exception(result.toString())
                        }
                    } catch (e: Exception) {
                        _screenState.value = SignUpUIState(error = e.message)
                    }
                }
            }
            is SignUpScreenEvent.NavigateToLogin -> {
                viewModelScope.launch {
                    _uiChannel.send(Navigate(event.route))
                }
            }
        }
    }

}