package com.houston.filmographer.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemMovieBinding
import com.houston.filmographer.domain.Movie

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
        return MovieViewHolder(ItemMovieBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(movies.get(position))
        holder.itemView.setOnClickListener { clickListener.onMovieClick(movies.get(position)) }
    }

    fun interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}