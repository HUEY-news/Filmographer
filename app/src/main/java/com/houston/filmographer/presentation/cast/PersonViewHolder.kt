package com.houston.filmographer.presentation.cast

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ItemPersonBinding

class PersonViewHolder(
    private val binding: ItemPersonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CastItem.PersonItem) {

        if (item.person.image == null) {
            binding.imageViewPerson.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.person.image)
                .into(binding.imageViewPerson)
            binding.imageViewPerson.isVisible = true
        }
        binding.textViewPersonName.text = item.person.name
        binding.textViewPersonDescription.text = item.person.description
    }
}