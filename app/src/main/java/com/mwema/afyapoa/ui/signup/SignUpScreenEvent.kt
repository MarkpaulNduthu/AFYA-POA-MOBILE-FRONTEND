package com.mwema.afyapoa.ui.signup

sealed class SignUpScreenEvent {
    object SignUP: SignUpScreenEvent()
    data class NavigateToLogin(val route: String): SignUpScreenEvent()
}