package com.mwema.afyapoa.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthAPIInterceptor(
    val tokenManager: TokenManager
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        println("TOKEN FROM MANAGER $token")
        val response = if (token != null){
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }else {
            chain.request()
        }
        return chain.proceed(response)
    }
}