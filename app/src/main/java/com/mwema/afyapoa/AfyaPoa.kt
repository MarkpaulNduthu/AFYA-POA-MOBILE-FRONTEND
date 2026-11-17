package com.mwema.afyapoa

import android.app.Application
import com.mwema.afyapoa.data.network.AfyaPoaAPI
import com.mwema.afyapoa.data.network.AuthAPIInterceptor
import com.mwema.afyapoa.data.network.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class AfyaPoa : Application() {
    lateinit var tokenManager: TokenManager
        private set
    lateinit var api: AfyaPoaAPI
        private set

    override fun onCreate() {
        super.onCreate()
        tokenManager = TokenManager(this)
        val BASE_URL = "https://9cb5-102-210-42-254.ngrok-free.app/api/v1/".trim()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthAPIInterceptor(tokenManager))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AfyaPoaAPI::class.java)

    }
}