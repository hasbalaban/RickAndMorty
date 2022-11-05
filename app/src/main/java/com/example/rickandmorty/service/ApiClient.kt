package com.example.rickandmorty.service

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val gson : Gson = Gson()
    private const val TIMEOUT_SECONDS = 30L
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(newClient)
            .build()
        return@lazy retrofit.create(ApiService::class.java)
    }

    private val newClient: OkHttpClient
        get() {
            val okhttpClient = OkHttpClient().newBuilder()
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

            return okhttpClient.build()
        }
}