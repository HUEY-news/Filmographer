package com.houston.filmographer.presentation.search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ItemMovieBinding
import com.houston.filmographer.domain.search.model.Movie

class SearchViewHolder(
private val binding: ItemMovieBinding,
private val clickListener: SearchAdapter.MovieClickListener
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(movie: Movie) {

        Glide.with(itemView)
            .load(movie.image)
            .into(binding.imageViewMovieCover)

        binding.textViewMovieTitle.text = movie.title
        binding.textViewMovieDescription.text = movie.description
        binding.imageViewFavorite.setImageDrawable(getFavoriteDrawable(movie.inFavorite))

        itemView.setOnClickListener { clickListener.onMovieClick(movie) }
        binding.imageViewFavorite.setOnClickListener { clickListener.onFavoriteClick(movie) }
    }

    private fun getFavoriteDrawable(inFavorite: Boolean): Drawable? {
        return itemView.context.getDrawable(
            if (inFavorite) R.drawable.favorite_active
            else R.drawable.favorite_inactive
        )
    }
}