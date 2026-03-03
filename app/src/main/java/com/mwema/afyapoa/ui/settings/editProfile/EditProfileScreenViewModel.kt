package com.mwema.afyapoa.ui.settings.editProfile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwema.afyapoa.data.ApiImplementation
import kotlinx.coroutines.launch

class EditProfileScreenViewModel(
    val apiImplementation: ApiImplementation
) : ViewModel() {

    var uiState by mutableStateOf(EditProfileUIState())
        private set
    var currentUserName by mutableStateOf("")
    var profileImageUrl by mutableStateOf("")
        private set

    fun updateProfile(context: Context, userName: String, imageUrl: Uri) {
        viewModelScope.launch {
            uiState = uiState.copy(isUpdating = true)
            val response = apiImplementation.updateProfile(context, imageUrl, userName)
            response.fold(
                onSuccess = { result ->
                    uiState = uiState.copy(
                        isUpdating = false,
                        success = true
                    )
                    currentUserName = result.userName
                    profileImageUrl = result.imageUrl
                },
                onFailure = { throwable ->
                    uiState = uiState.copy(
                        isUpdating = false,
                        success = false,
                        error = throwable.message
                    )
                }
            )
        }
    }

    fun clearSuccess() {
        uiState = uiState.copy(success = false)
    }

    fun clearError() {
        uiState = uiState.copy(error = null)
    }
}