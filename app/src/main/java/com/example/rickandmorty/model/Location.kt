package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location (
    @SerializedName("name" ) var locationName : String,
    @SerializedName("url"  ) var locationUrl  : String
) : Serializable