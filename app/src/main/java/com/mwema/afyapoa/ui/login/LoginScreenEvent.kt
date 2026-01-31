package com.mwema.afyapoa.ui.login

sealed class LoginScreenEvent {
    object Login: LoginScreenEvent()
    data class NavigateToSignUp(val route : String): LoginScreenEvent()
    data class NavigateToHome(val route : String): LoginScreenEvent()
}