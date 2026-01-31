package com.mwema.afyapoa.data

import android.util.Log
import com.mwema.afyapoa.data.model.AuthenticationRequest
import com.mwema.afyapoa.data.network.AfyaPoaAPI
import com.mwema.afyapoa.data.network.TokenManager


class ApiImplementation(
    private val afyaPoaAPI: AfyaPoaAPI,
    private val tokenManager: TokenManager
) : ApiInterface {
    val TAG = "API-IMPLEMENTATION"
    override suspend fun login(authenticationRequest: AuthenticationRequest): Result<Unit> {
        return try {
            val response = afyaPoaAPI.login(authenticationRequest)
            if(response.isSuccessful) {
                println(response.raw())
                Log.i(TAG, response.body()?.token!!)
                val token = response.body()?.token
                if ( token != null) {
                    Log.i(TAG, "SAVING TOKEN TO SHARED PREFERENCE")
                    tokenManager.saveToken(token)
                    Log.i(TAG, token)
                    Result.success(Unit)
                } else {
                    Log.e(TAG, "token is null ${response.body()?.error}")
                    Result.failure(Exception("Failed to obtain Token"))
                }
            } else {
                Log.e(TAG,"Request Unsuccesful")
                Result.failure(Exception("Network error"))
            }
        }catch (e: Exception){
            Log.e(TAG,"${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun register(authenticationRequest: AuthenticationRequest): Result<Unit> {
        return try {
            val response = afyaPoaAPI.register(authenticationRequest)
            if(response.isSuccessful){
                Log.i(TAG,response.body().toString())
                return Result.success(Unit)
            }else{
                Log.i(TAG,response.body()?.error!!)
                return Result.failure(Exception(response.body()?.error))
            }
        }catch (e: Exception){
            Log.e(TAG,"${e.message}")
            return Result.failure(e)
        }
    }
}