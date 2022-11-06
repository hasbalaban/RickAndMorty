package com.example.rickandmorty.service

import com.example.rickandmorty.model.ResultDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    fun getCharacterData (@Query("page") page : Int) : Call<ResultDetails>
}