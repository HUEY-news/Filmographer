package com.houston.filmographer.presentation.history

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ItemHistoryBinding
import com.houston.filmographer.domain.model.Movie

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(movie: Movie) {

        Glide.with(itemView)
            .load(movie.image)
            .into(binding.movieCover)

        binding.movieTitle.text = movie.title
        binding.movieDescription.text = movie.description
    }
}