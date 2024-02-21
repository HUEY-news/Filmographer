package com.houston.filmographer.presentation.cast

import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemHeaderBinding

class HeaderViewHolder(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CastItem.HeaderItem) {
        binding.textViewHeader.text = item.header
    }
}