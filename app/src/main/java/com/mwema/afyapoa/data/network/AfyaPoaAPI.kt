package com.mwema.afyapoa.data.network

import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.data.model.AuthenticationResponse
import com.mwema.afyapoa.data.model.LoadProfileResponse
import com.mwema.afyapoa.data.model.LoginAuthenticationRequest
import com.mwema.afyapoa.data.model.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface AfyaPoaAPI {
    @POST("auth/login")
    suspend fun login(@Body authenticationRequest: LoginAuthenticationRequest): Response<AuthenticationResponse>

    @POST("auth/register")
    suspend fun register(@Body authenticationRequest: AuthenticationRequest): Response<AuthenticationResponse>

    @Multipart
    @POST("profiles/update-profile")
    suspend fun updateProfile(
        @Part profileImage: MultipartBody.Part,
        @Part("userName") userName: RequestBody
    ): Response<UpdateProfileResponse>

    @GET("profiles/student-profile")
    suspend fun loadProfile(): Response<LoadProfileResponse>
}