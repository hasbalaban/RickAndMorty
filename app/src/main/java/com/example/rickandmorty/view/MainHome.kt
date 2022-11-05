package com.example.rickandmorty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentMainHomeBinding

class MainHome : Fragment(R.layout.fragment_main_home) {

    private var _binding : FragmentMainHomeBinding? = null
    private val binding : FragmentMainHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainHomeBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}