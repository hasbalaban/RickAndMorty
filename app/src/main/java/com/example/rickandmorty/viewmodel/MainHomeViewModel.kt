package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.model.ResultDetails
import com.example.rickandmorty.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeViewModel : ViewModel() {
    val charactersResponse = MutableLiveData<ResultDetails>()
    val currentPage = MutableLiveData<Int>()

    fun getCharacterData(page : Int = 1){
        currentPage.value = page
        ApiClient.apiService.getCharacterData(page).enqueue(object : Callback<ResultDetails>{
            override fun onResponse(call: Call<ResultDetails>, response: Response<ResultDetails>) {
                response.body()?.let {
                    charactersResponse.value = it
                    println(it)
                }
            }

            override fun onFailure(call: Call<ResultDetails>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}