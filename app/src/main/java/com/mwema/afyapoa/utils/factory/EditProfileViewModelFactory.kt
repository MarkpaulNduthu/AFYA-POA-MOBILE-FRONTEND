package com.mwema.afyapoa.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.settings.editProfile.EditProfileScreenViewModel

class EditProfileViewModelFactory(
    val apiImplementation: ApiImplementation
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(EditProfileScreenViewModel::class.java)){
            return EditProfileScreenViewModel(apiImplementation = apiImplementation) as T
        }
        throw IllegalArgumentException("Failed to create EditProfile viewModel factory")
    }
}