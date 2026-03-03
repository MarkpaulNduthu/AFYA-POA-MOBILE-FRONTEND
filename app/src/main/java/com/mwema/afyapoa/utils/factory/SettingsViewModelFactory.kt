package com.mwema.afyapoa.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.settings.SettingsViewModel

class SettingsViewModelFactory(
    private val apiImplementation: ApiImplementation
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(apiImplementation) as T
        }
        throw IllegalArgumentException("error creating settings view model in factory")
    }
}