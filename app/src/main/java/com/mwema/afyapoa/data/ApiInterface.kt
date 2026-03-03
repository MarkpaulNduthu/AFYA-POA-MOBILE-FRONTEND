package com.mwema.afyapoa.data

import android.content.Context
import android.net.Uri
import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.data.model.LoadProfileResponse
import com.mwema.afyapoa.data.model.LoginAuthenticationRequest
import com.mwema.afyapoa.data.model.UpdateProfileResponse

interface ApiInterface {
    suspend fun login(authenticationRequest: LoginAuthenticationRequest) : Result<Unit>
    suspend fun register(authenticationRequest: AuthenticationRequest) : Result<Unit>
    suspend fun updateProfile(context: Context,imageUri: Uri,userName:String): Result<UpdateProfileResponse>
    suspend fun loadProfile(): Result<LoadProfileResponse>
}