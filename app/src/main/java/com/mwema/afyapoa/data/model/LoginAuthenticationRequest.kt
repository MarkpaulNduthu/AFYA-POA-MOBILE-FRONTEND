package com.mwema.afyapoa.data.model

data class LoginAuthenticationRequest(
    val registrationNumber: String,
    val password: String
)