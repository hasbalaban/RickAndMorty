package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Info (
    @SerializedName("count" ) var count : Int,
    @SerializedName("pages" ) var pages : Int,
    @SerializedName("next"  ) var next  : String?,
    @SerializedName("prev"  ) var prev  : String?
) : Serializable