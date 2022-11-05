package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        setDefaultConfig()
        setListeners()
        subscribeObservers()
        getData()
    }


    private fun getData(){

    }

    private fun setAdapter(data: String) {
        println(data)
    }

    private fun setListeners(){

    }

    private fun setDefaultConfig(){

    }

    private fun subscribeObservers(){
        setAdapter("data")
        setAdapter("data new")
    }

}