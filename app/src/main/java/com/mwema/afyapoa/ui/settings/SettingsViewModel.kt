package com.mwema.afyapoa.ui.settings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    val apiImplementation: ApiImplementation
) : ViewModel() {

    private val _uiChannel = Channel<UIEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()
    var userName by mutableStateOf("")
    var registrationNumber by mutableStateOf("")
    var imageUrl by mutableStateOf("")
    var uiState by mutableStateOf(SettingsUIState())
        private set

    fun executeEvent(event: SettingsScreenEvents) {
        when (event) {
            is SettingsScreenEvents.OnProfileCardClicked -> {
                viewModelScope.launch {
                    _uiChannel.send(UIEvent.Navigate(Routes.EDITPROFILE))
                }
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            uiState.isLoading = true
            val response = apiImplementation.loadProfile()
            response.fold(
                onSuccess = { result ->
                    uiState.isLoading = false
                    uiState.isSuccess = true
                    userName = result.userName
                    registrationNumber = result.registrationNumber
                    imageUrl = result.imageUrl
                },
                onFailure = { throwable ->
                    Log.i("SettingsScreen View-Model(method: loadProfile):",throwable.message!!)
                    uiState.isLoading = false
                    uiState.isSuccess = false
                    uiState.error = "Failed to load Profile"
                }
            )
        }
    }

    fun clearError() {
        uiState.error = null
    }

    fun clearSuccess() {
        uiState.isSuccess = false
    }
}