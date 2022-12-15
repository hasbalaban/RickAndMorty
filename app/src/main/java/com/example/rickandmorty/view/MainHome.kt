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
import com.example.rickandmorty.model.saveData
import com.example.rickandmorty.viewmodel.MainHomeViewModel

class MainHome : Fragment(), PagingAdapter.ClickPageButton {

    private var _binding : FragmentMainHomeBinding? = null
    private val binding : FragmentMainHomeBinding get() = _binding!!
    private val viewModel by viewModels<MainHomeViewModel>()
    private val adapterPaging : PagingAdapter by lazy {
        PagingAdapter(0, this)
    }
    private val adapterCharacter : CharacterAdapter  by lazy{
        CharacterAdapter()
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

    private fun setDefaultConfig() {
        val snapHelper1 = PagerSnapHelper()
        val snapHelper2 = PagerSnapHelper()
        binding.apply {
            snapHelper1.attachToRecyclerView(characterRecyclerview)
            snapHelper2.attachToRecyclerView(pagingRecyclerview)
            characterRecyclerview.adapter = adapterCharacter
            pagingRecyclerview.adapter = adapterPaging
        }
    }

    private fun getData(){
        viewModel.getCharacterData(context = requireContext())
    }

    private fun setAdapter(resultDetails: List<saveData>) {
        adapterCharacter.updateCharacterList(resultDetails)
        viewModel.pagingInfo.value?.pageSize?.let {
            if (adapterPaging.pageSize != it){
                adapterPaging.updatePageSize(it)
            }
        }
    }

    private fun subscribeObservers(){
        viewModel.charactersResponse.observe(viewLifecycleOwner){
            it?.let {
                setAdapter(it)
                binding.pagingRecyclerview.scrollToPosition(viewModel.pagingInfo.value?.currentPage ?: 0)
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
        if (clickedPage == viewModel.pagingInfo.value?.currentPage) return
        viewModel.getCharacterData(clickedPage, context = requireContext())
        adapterPaging.updatedSelectedPage(clickedPage)
    }
}