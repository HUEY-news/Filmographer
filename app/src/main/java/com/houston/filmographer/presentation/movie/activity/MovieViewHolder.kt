package com.houston.filmographer.presentation.movie.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ItemMovieBinding
import com.houston.filmographer.domain.model.Movie

class MovieViewHolder(
private val binding: ItemMovieBinding,
private val clickListener: MovieAdapter.MovieClickListener
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(movie: Movie) {

        Glide.with(itemView)
            .load(movie.image)
            .into(binding.imageViewCover)

        binding.textViewTitle.text = movie.title
        binding.textViewDescription.text = movie.description
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