package com.example.rickandmorty.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    private fun setDefaultConfig() {
        val snapHelper1 = PagerSnapHelper()
        val snapHelper2 = PagerSnapHelper()
        snapHelper1.attachToRecyclerView(binding.characterRecyclerview)
        snapHelper2.attachToRecyclerView(binding.pagingRecyclerview)
        binding.pagingRecyclerview.adapter = adapterPaging
    }

    private fun subscribeObservers(){
        viewModel.charactersResponse.observe(viewLifecycleOwner){
            it?.let {
                setAdapter(it)
                viewModel.updateDatabase(requireContext(), it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.apply {
                    loadingProgressBar.visibility = View.VISIBLE
                    mainContent.visibility = View.GONE
                }
                return@observe
            }
            binding.apply {
                loadingProgressBar.visibility = View.GONE
                mainContent.visibility = View.VISIBLE
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
        adapterPaging.updatedSelectedPage(clickedPage)
    }
}