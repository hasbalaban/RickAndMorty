package com.example.rickandmorty.database

import androidx.room.*
import com.example.rickandmorty.model.ResultDetailsDatabase

@Dao
interface CharacterPageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPage(vararg resultDetailsDatabase: ResultDetailsDatabase) : List<Long>

    @Query("DELETE FROM ResultDetailsDatabase")
    suspend fun deleteAll()

    @Query("select * from ResultDetailsDatabase")
    suspend fun getPage () : List<ResultDetailsDatabase>
}