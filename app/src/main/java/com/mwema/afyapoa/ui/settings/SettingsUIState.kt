package com.mwema.afyapoa.ui.settings

data class SettingsUIState(
    var isSuccess: Boolean = false,
    var error: String? = null,
    var isLoading: Boolean = false
)
