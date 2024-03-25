package com.houston.filmographer.presentation.history

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemHistoryBinding
import com.houston.filmographer.domain.search.model.Movie

class HistoryAdapter: RecyclerView.Adapter<HistoryViewHolder>() {

    private var movies = listOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
        Log.d("TEST", "Adapter.setData(): $movies")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInspector, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(movies.get(position))
        Log.d("TEST", "Adapter.bind(): ${movies.get(position)}")
    }
}