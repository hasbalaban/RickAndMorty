package com.example.rickandmorty.service

import com.example.rickandmorty.model.ResultDetails
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getAll () : Call<ResultDetails>
}