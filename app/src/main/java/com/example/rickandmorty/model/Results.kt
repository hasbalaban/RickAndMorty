package com.example.rickandmorty.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName


data class Results(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("status") var status: String,
    @SerializedName("species") var species: String,
    @SerializedName("type") var type: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("origin") var character: Character,
    @SerializedName("location") var location: Location,
    @SerializedName("image") var image: String,
    @SerializedName("episode") var episode: ArrayList<String>,
    @SerializedName("url") var url: String,
    @SerializedName("created") var created: String,
) :java.io.Serializable