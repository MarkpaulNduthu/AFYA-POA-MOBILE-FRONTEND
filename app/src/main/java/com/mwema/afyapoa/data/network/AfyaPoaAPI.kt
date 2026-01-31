package com.mwema.afyapoa.data.network

import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.data.model.AuthenticationResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AfyaPoaAPI {
    @POST("auth/login")
    suspend fun login( @Body authenticationRequest: AuthenticationRequest) : Response<AuthenticationResponse>
    @POST("auth/register")
    suspend fun register(@Body authenticationRequest: AuthenticationRequest) : Response<AuthenticationResponse>
}