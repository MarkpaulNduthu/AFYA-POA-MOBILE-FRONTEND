package com.mwema.afyapoa.ui.login

data class LoginUIState(
    val isLoading : Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {}