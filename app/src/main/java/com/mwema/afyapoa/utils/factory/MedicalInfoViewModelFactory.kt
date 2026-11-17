package com.mwema.afyapoa.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.settings.profileInfo.MedicalInfoViewModel

class MedicalInfoViewModelFactory(
    val apiImplementation: ApiImplementation
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MedicalInfoViewModel::class.java)){
            MedicalInfoViewModel(apiImplementation) as T
        }else{
            throw IllegalArgumentException("Failed to create EditProfile viewModel factory")
        }
    }
}