package com.houston.filmographer.presentation

import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemLocationBinding
import com.houston.filmographer.domain.Location

class LocationViewHolder(
    private val binding:ItemLocationBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.textView.text = "${location.name} (${location.country})"
        }

}