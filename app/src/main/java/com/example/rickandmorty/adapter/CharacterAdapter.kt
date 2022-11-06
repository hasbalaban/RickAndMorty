package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharacterItemBinding
import com.example.rickandmorty.model.ResultDetails

class CharacterAdapter (private val resultDetails : ResultDetails) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    class ViewHolder(val binding : CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CharacterItemBinding.inflate(inflater, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.resultDetails = resultDetails.results[position]
    }

    override fun getItemCount() = resultDetails.results.size
}

