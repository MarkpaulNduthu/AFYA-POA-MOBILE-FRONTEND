package com.mwema.afyapoa.ui.signup

data class SignUpUIState(
    val isLoading : Boolean = false,
    val error: String? = null,
    val success: Boolean = false
){}