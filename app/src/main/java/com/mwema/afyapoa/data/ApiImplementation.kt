package com.mwema.afyapoa.data

import android.content.Context
import android.net.Uri
import android.util.Log


import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.data.model.LoadProfileResponse
import com.mwema.afyapoa.data.model.LoginAuthenticationRequest
import com.mwema.afyapoa.data.model.UpdateProfileResponse
import com.mwema.afyapoa.data.network.AfyaPoaAPI
import com.mwema.afyapoa.data.network.TokenManager
import com.mwema.afyapoa.utils.toFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class ApiImplementation(
    private val afyaPoaAPI: AfyaPoaAPI,
    private val tokenManager: TokenManager
) : ApiInterface {
    val TAG = "API-IMPLEMENTATION"
    override suspend fun login(authenticationRequest: LoginAuthenticationRequest): Result<Unit> {
        return try {
            val response = afyaPoaAPI.login(authenticationRequest)
            if (response.isSuccessful) {
                println(response.raw())
                Log.i(TAG, response.body()?.token!!)
                val token = response.body()?.token
                if (token != null) {
                    Log.i(TAG, "SAVING TOKEN TO SHARED PREFERENCE")
                    tokenManager.saveToken(token)
                    Log.i(TAG, token)
                    Result.success(Unit)
                } else {
                    Log.e(TAG, "token is null ${response.body()?.error}")
                    Result.failure(Exception("Failed to obtain Token"))
                }
            } else {
                Log.e(TAG, "Request Unsuccesful")
                Result.failure(Exception("Network error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun register(authenticationRequest: AuthenticationRequest): Result<Unit> {
        try {
            val response = afyaPoaAPI.register(authenticationRequest)
            if (response.isSuccessful) {
                Log.i(TAG, response.body().toString())
                return Result.success(Unit)
            } else {
                Log.i(TAG, response.body()?.error!!)
                return Result.failure(Exception(response.body()?.error))
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        context: Context,
        imageUri: Uri,
        userName: String
    ): Result<UpdateProfileResponse> {
        val file = imageUri.toFile(context)
        val requestBody =
            file.asRequestBody("image/*".toMediaType())

        val part = MultipartBody.Part.createFormData(
            "profileImage",
            file.name,
            requestBody
        )
        val userName2 = userName.toRequestBody("text/plain".toMediaType())

        val response = afyaPoaAPI.updateProfile(part, userName2)

        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Upload failed"))
        }
    }

    override suspend fun loadProfile(): Result<LoadProfileResponse> {
        val response = afyaPoaAPI.loadProfile()
        if (response.isSuccessful && response.body() != null) {
            return Result.success(response.body()!!)
        }
        return Result.failure(Exception("Failed to fetch profile"))
    }
}