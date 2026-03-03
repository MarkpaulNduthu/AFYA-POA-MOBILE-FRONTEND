package com.mwema.afyapoa

import android.app.Application
import com.mwema.afyapoa.data.network.AfyaPoaAPI
import com.mwema.afyapoa.data.network.AuthAPIInterceptor
import com.mwema.afyapoa.data.network.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class AfyaPoa : Application() {
    lateinit var tokenManager: TokenManager
        private set
    lateinit var api: AfyaPoaAPI
        private set

    override fun onCreate() {
        super.onCreate()
        tokenManager = TokenManager(this)
        val BASE_URL = "https://65a7-102-210-42-254.ngrok-free.app/api/v1/".trim()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthAPIInterceptor(tokenManager))
            .build()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AfyaPoaAPI::class.java)

    }
}