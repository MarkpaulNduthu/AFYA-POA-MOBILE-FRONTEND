package com.mwema.afyapoa.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.login.LoginScreenViewModel


class LoginViewModelFactory(
    private val apiImplementation: ApiImplementation
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(LoginScreenViewModel::class.java)) {
            return LoginScreenViewModel(apiImplementation) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }
}