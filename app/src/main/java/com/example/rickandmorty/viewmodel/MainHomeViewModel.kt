package com.example.rickandmorty.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.database.Database
import com.example.rickandmorty.model.ResultDetails
import com.example.rickandmorty.model.saveData
import com.example.rickandmorty.service.ApiClient
import com.example.rickandmorty.utils.transformCharacterSaveList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeViewModel : ViewModel() {
    val charactersResponse = MutableLiveData<List<saveData>>()
    val currentPage = MutableLiveData<Int>()
    val pageSize = MutableLiveData<Int>()
    val isLoading = MutableLiveData(true)

    fun getCharacterData(page: Int = 1, context: Context) {
        isLoading.value = true
        currentPage.value = page
        ApiClient.apiService.getCharacterData(page).enqueue(object : Callback<ResultDetails> {
            override fun onResponse(call: Call<ResultDetails>, response: Response<ResultDetails>) {
                isLoading.value = false
                response.body()?.results?.let { results ->
                    CoroutineScope(Dispatchers.IO).launch {
                        Database.getDatabase(context).dao().deleteAll()
                    }
                    pageSize.value = results.size
                    charactersResponse.value =
                        results.transformCharacterSaveList(currentPage = currentPage.value ?: 0)
                }
            }

            override fun onFailure(call: Call<ResultDetails>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun updateDatabase(context: Context, resultDetails: List<saveData>) {
        val database = Database.getDatabase(context).dao()
        CoroutineScope(Dispatchers.IO).launch {
            database.addPage(*resultDetails.toTypedArray())
        }
    }

    fun getDataFromDatabase(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = Database.getDatabase(context).dao()
            val databaseData = dao.getPage()
            if (databaseData.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    pageSize.value = 10
                    currentPage.value = databaseData.firstOrNull()?.pageNumber
                    charactersResponse.value = databaseData
                    isLoading.value = false
                }
            }
        }
    }
}