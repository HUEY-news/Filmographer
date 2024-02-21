package com.houston.filmographer.presentation.cast

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ItemCastBinding
import com.houston.filmographer.domain.model.Person

class CastViewHolder(
    private val binding: ItemCastBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {

        if (person.image == null) {
            binding.imageViewPerson.isVisible = false
        } else {
            Glide.with(itemView)
                .load(person.image)
                .into(binding.imageViewPerson)
            binding.imageViewPerson.isVisible = true
        }
        binding.textViewPersonName.text = person.name
        binding.textViewPersonDescription.text = person.description
    }
}