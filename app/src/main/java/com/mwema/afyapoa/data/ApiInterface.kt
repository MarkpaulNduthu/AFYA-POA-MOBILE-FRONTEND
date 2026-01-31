package com.mwema.afyapoa.data

import com.mwema.afyapoa.data.model.AuthenticationRequest
import retrofit2.Response
import retrofit2.http.Body

interface ApiInterface {
    suspend fun login( @Body authenticationRequest: AuthenticationRequest) : Result<Unit>
    suspend fun register(@Body authenticationRequest: AuthenticationRequest) : Result<Unit>
}