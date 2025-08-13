package com.example.buibinhminh.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private var gsonConfig = GsonBuilder().create()
object HomeApiClient {
    private const val BASE_URL = "https://ws.audioscrobbler.com"
    private const val REQUEST_TIMEOUT = 30L

    private val retrofit: Retrofit by lazy { buildRetrofit() }
    fun build(): HomeApiService { return retrofit.create(HomeApiService::class.java)}
    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .build()
    }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}