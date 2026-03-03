package com.mwema.afyapoa.data.model

data class AuthenticationRequest(
    val registrationNumber: String,
    val email: String,
    val password: String
)
