package com.example.rickandmorty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.rickandmorty.adapter.CharacterAdapter
import com.example.rickandmorty.adapter.PagingAdapter
import com.example.rickandmorty.databinding.FragmentMainHomeBinding
import com.example.rickandmorty.model.ResultDetails
import com.example.rickandmorty.viewmodel.MainHomeViewModel

class MainHome : Fragment(), PagingAdapter.ClickPageButton {

    private var _binding : FragmentMainHomeBinding? = null
    private val binding : FragmentMainHomeBinding get() = _binding!!
    private val viewModel by viewModels<MainHomeViewModel>()
    private val adapterPaging =  PagingAdapter(0, this)

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
        viewModel.getCharacterData()
    }

    private fun setAdapter(resultDetails: ResultDetails) {

        val adapterCharacter = CharacterAdapter(resultDetails)
        binding.characterRecyclerview.adapter = adapterCharacter

        resultDetails.info.pages.let {
            if (adapterPaging.pageSize != it){
                adapterPaging.updatePageSize(it)
            }
        }
    }

    private fun setListeners(){

    }

    private fun setDefaultConfig(){
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.characterRecyclerview)
        snapHelper.attachToRecyclerView(binding.pagingRecyclerview)
        binding.pagingRecyclerview.adapter = adapterPaging
    }

    private fun subscribeObservers(){
        viewModel.charactersResponse.observe(viewLifecycleOwner){
            it?.let {
                setAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickPageButton(clickedPage: Int) {
        if (clickedPage == viewModel.currentPage.value) return
        viewModel.getCharacterData(clickedPage)
    }
}