package com.example.rickandmorty.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.database.CharacterPageDao
import com.example.rickandmorty.database.Database
import com.example.rickandmorty.model.PagingInfo
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
    val pagingInfo = MutableLiveData<PagingInfo>()
    val isLoading = MutableLiveData(true)


    fun getCharacterData(page: Int = 1, context: Context) {
        isLoading.value = true
        val dao = Database.getDatabase(context).dao()
        getDataFromDatabase(dao)
        pagingInfo.value = pagingInfo.value?.copy(currentPage = page)

        ApiClient.apiService.getCharacterData(page).enqueue(object : Callback<ResultDetails> {
            override fun onResponse(call: Call<ResultDetails>, response: Response<ResultDetails>) {
                isLoading.value = false
                response.body()?.results?.let { results ->
                    pagingInfo.value = PagingInfo(
                        currentPage = page,
                        pageSize = results.size
                    )
                    val saveList = results.transformCharacterSaveList(
                        currentPage = pagingInfo.value?.currentPage ?: 0
                    )
                    charactersResponse.value = saveList

                    CoroutineScope(Dispatchers.IO).launch {
                        dao.deleteAll()
                        updateDatabase(dao, saveList)
                    }
                }
            }

            override fun onFailure(call: Call<ResultDetails>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    private suspend fun updateDatabase(dao: CharacterPageDao, resultDetails: List<saveData>) {
            dao.addPage(*resultDetails.toTypedArray())
    }

    private fun getDataFromDatabase(dao: CharacterPageDao) {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseData = dao.getPage()
            if (databaseData.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    pagingInfo.value = PagingInfo(currentPage = 1, pageSize = 1)
                    charactersResponse.value = databaseData
                }
            }
        }
    }
}