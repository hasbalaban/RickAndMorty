package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.PagingItemBinding

class PagingAdapter(
    val pageSize: Int,
    private val clickPageButton : ClickPageButton
) : RecyclerView.Adapter<PagingAdapter.ViewHolder>() {
    private var adapterPageSize = pageSize
    class ViewHolder(val binding: PagingItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = PagingItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            pageNumber.text = "${position + 1}"
            pageNumber.setOnClickListener {
                clickPageButton.clickPageButton(position+1)
            }
        }
    }


    class PageSizeDiffCallback(
        private val olsSize: Int,
        private val newSize: Int
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemPosition == newItemPosition
        }

        override fun getOldListSize() = olsSize

        override fun getNewListSize() = newSize

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemPosition == newItemPosition
        }
    }

    fun updatePageSize(newSize: Int){
        val diffCallback = PageSizeDiffCallback(adapterPageSize, newSize)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        adapterPageSize = newSize
        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = adapterPageSize

    interface ClickPageButton {
        fun clickPageButton(clickedPage: Int)
    }
}

