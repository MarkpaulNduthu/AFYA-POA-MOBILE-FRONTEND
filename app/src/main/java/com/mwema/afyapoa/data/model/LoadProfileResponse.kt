package com.mwema.afyapoa.data.model

data class LoadProfileResponse(
    val isSuccess: Boolean,
    val userName: String = "",
    val registrationNumber: String = "",
    val imageUrl: String = ""
)
