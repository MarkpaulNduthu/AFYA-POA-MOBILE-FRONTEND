package com.mwema.afyapoa

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwema.afyapoa.utils.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    var uiState by mutableStateOf(MainActivityUIState())
        private set

    fun getStartDestination(applicationContext: AfyaPoa): String {
        Thread.sleep(5000)
        uiState = uiState.copy(isValidating = true)
        val route = applicationContext.tokenManager.getToken()?.let {
            Routes.MAINSCREEN
        } ?: Routes.LOGIN
        uiState = uiState.copy(isValidating = false)
        return route
    }
}