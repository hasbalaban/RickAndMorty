package com.example.rickandmorty.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmorty.model.ResultDetailsDatabase

@androidx.room.Database(entities = [ResultDetailsDatabase::class], version = 2, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun dao (): CharacterPageDao

    companion object{
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            return INSTANCE ?: synchronized(this){
                provideDatabase(context)
            }
        }

        private fun provideDatabase(context: Context): Database {
            return Room.databaseBuilder(
                context.applicationContext,
                Database::class.java, "character-database"
            ).fallbackToDestructiveMigration().build()
        }

    }

}