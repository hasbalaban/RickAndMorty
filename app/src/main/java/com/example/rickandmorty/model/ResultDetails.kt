package com.example.rickandmorty.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class ResultDetails(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Results>
) : Serializable