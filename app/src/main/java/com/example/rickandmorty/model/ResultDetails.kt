package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class ResultDetails(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: ArrayList<Results>
)