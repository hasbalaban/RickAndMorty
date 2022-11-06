package com.example.rickandmorty.database

import androidx.room.*
import com.example.rickandmorty.model.ResultDetailsDatabase

@Dao
interface CharacterPageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPage(vararg resultDetailsDatabase: ResultDetailsDatabase)

    @Query("DELETE FROM ResultDetailsDatabase")
    suspend fun deleteAll()

    @Query("select * from ResultDetailsDatabase")
    suspend fun getPage () : ResultDetailsDatabase
}