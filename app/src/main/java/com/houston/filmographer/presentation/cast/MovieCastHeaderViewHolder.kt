package com.houston.filmographer.presentation.cast

import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemMovieCastHeaderBinding

class MovieCastHeaderViewHolder(
    private val binding: ItemMovieCastHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieCastItem.HeaderItem) {
        binding.textViewHeader.text = item.header
    }
}