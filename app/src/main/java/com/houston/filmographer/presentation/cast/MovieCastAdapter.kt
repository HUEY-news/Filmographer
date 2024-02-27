package com.houston.filmographer.presentation.cast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ItemMovieCastHeaderBinding
import com.houston.filmographer.databinding.ItemMovieCastPersonBinding

class MovieCastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<MovieCastItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<MovieCastItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        when (items.get(position)) {
            is MovieCastItem.HeaderItem -> return R.layout.item_movie_cast_header
            is MovieCastItem.PersonItem -> return R.layout.item_movie_cast_person
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val headerBinding = ItemMovieCastHeaderBinding.inflate(layoutInspector, parent, false)
        val personBinding = ItemMovieCastPersonBinding.inflate(layoutInspector, parent, false)

        when (viewType) {
            R.layout.item_movie_cast_header -> return MovieCastHeaderViewHolder(headerBinding)
            R.layout.item_movie_cast_person -> return MovieCastPersonViewHolder(personBinding)
            else -> error("НЕИЗВЕСТНЫЙ VIEW TYPE [$viewType]")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            R.layout.item_movie_cast_header -> {
                val headerHolder = holder as MovieCastHeaderViewHolder
                val headerItem = items.get(position) as MovieCastItem.HeaderItem
                headerHolder.bind(headerItem)
            }

            R.layout.item_movie_cast_person -> {
                val personHolder = holder as MovieCastPersonViewHolder
                val personItem = items.get(position) as MovieCastItem.PersonItem
                personHolder.bind(personItem)
            }
        }
    }
}