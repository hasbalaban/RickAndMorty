package com.example.rickandmorty.utils

import com.example.rickandmorty.model.Results
import com.example.rickandmorty.model.saveData

fun  List<Results>.transformCharacterSaveList(currentPage: Int): List<saveData> {
    val list = this.map {
        saveData(
            pageNumber = currentPage,
            name = it.name,
            imageUrl = it.image,
            status = it.status,
            species = it.species,
            lastKnowLocation = it.location.locationName,
            gender = it.gender,
        )
    }
    return list
}