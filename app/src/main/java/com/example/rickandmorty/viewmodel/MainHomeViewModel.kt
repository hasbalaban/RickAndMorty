package com.example.rickandmorty.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.database.Database
import com.example.rickandmorty.model.ResultDetails
import com.example.rickandmorty.model.saveData
import com.example.rickandmorty.service.ApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeViewModel : ViewModel() {
    val charactersResponse = MutableLiveData<ResultDetails>()
    val currentPage = MutableLiveData<Int>()
    val isLoading = MutableLiveData(true)

    fun getCharacterData(page : Int = 1){
        isLoading.value = true
        currentPage.value = page
        ApiClient.apiService.getCharacterData(page).enqueue(object : Callback<ResultDetails> {
            override fun onResponse(call: Call<ResultDetails>, response: Response<ResultDetails>) {
                isLoading.value = false
                response.body()?.let {
                    charactersResponse.value = it
                }
            }
            override fun onFailure(call: Call<ResultDetails>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun updateDatabase(context: Context, resultDetails: ResultDetails) {
        val saveDataList = resultDetails.results.map {
            saveData(
                pageNumber = currentPage.value ?: 1,
                name = it.name,
                imageUrl = it.image,
                state = it.status,
                species = it.species,
                lastKnowLocation = it.location.locationName,
                gender = it.gender,
            )
        }
        val database = Database.getDatabase(context).dao()
        viewModelScope.launch {
            database.addPage(*saveDataList.toTypedArray())
        }
    }

    private fun getDataFromDatabase(context: Context) {
        viewModelScope.launch {
            val database = Database.getDatabase(context).dao().getPage()
            println(database)
        }
    }
}