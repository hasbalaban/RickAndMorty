package com.example.rickandmorty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharacterAdapter
import com.example.rickandmorty.databinding.FragmentMainHomeBinding

class MainHome : Fragment() {

    private var _binding : FragmentMainHomeBinding? = null
    private val binding : FragmentMainHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.characterRecyclerview)

        val adapter = CharacterAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.characterRecyclerview.layoutManager = layoutManager
        binding.characterRecyclerview.adapter = adapter
    }

    private fun setListeners(){

    }

    private fun setDefaultConfig(){

    }

    private fun subscribeObservers(){
        setAdapter("data")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}