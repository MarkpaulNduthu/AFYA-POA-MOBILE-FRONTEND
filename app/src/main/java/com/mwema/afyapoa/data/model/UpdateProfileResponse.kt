package com.mwema.afyapoa.data.model

data class UpdateProfileResponse(
    val success: Boolean,
    val imageUrl: String,
    val userName: String,
    val error: String
)
