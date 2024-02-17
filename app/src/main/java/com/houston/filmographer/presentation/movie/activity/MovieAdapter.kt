package com.houston.filmographer.presentation.movie.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemMovieBinding
import com.houston.filmographer.domain.model.Movie

class MovieAdapter(
    private val clickListener: MovieClickListener
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies = listOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContent(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInspector, parent, false)
        return MovieViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
        fun onFavoriteClick(movie: Movie)
    }
}