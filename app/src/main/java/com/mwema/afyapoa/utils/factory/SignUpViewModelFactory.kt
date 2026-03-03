package com.mwema.afyapoa.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.signup.SignUPViewModel

class SignUpViewModelFactory(
    private val apiImplementation: ApiImplementation
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SignUPViewModel::class.java)){
            return SignUPViewModel(apiImplementation) as T
        }
        throw Exception("Error creating signup view model in factory")
    }
}