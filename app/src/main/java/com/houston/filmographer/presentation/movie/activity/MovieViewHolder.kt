package com.houston.filmographer.presentation.movie.activity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ItemMovieBinding
import com.houston.filmographer.domain.model.Movie

class MovieViewHolder(
private val binding: ItemMovieBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {

        Glide.with(itemView)
            .load(movie.image)
            .into(binding.imageView)

        binding.textViewTitle.text = movie.title
        binding.textViewDescription.text = movie.description
    }
}