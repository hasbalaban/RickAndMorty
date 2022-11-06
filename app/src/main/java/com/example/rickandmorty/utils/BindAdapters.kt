package com.example.rickandmorty.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmorty.R

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl : String) {
    val size = if (imageView.width <= 0 ) 1 else imageView.width
    imageView.load(imageUrl) {
        crossfade(true)
        transformations(CircleCropTransformation())
        size(size)
    }
}

@BindingAdapter("status")
fun status(imageView: ImageView, status : String) {
    imageView.setImageResource(when(status){
        imageView.resources.getString(R.string.dead) -> R.drawable.dead
        imageView.resources.getString(R.string.alive) -> R.drawable.alive
        else -> R.drawable.unknown
    })

}