package com.example.rickandmorty.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ResultDetailsDatabase(
    @ColumnInfo val pageNumber: Int,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val name: String,
    @ColumnInfo val state: String,
    @ColumnInfo val species: String,
    @ColumnInfo val lastKnowLocation: String,
    @ColumnInfo val gender: String,
){
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
}

typealias saveData = ResultDetailsDatabase