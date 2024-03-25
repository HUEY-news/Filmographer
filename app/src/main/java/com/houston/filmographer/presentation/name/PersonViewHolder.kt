package com.houston.filmographer.presentation.name

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ItemPersonBinding
import com.houston.filmographer.domain.search.model.Person

class PersonViewHolder(
    private val binding: ItemPersonBinding
    ): RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {
        Glide.with(itemView)
            .load(person.photoUrl)
            .circleCrop()
            .into(binding.imageViewPerson)

        binding.textViewPersonName.text = person.name
        binding.textViewPersonDescription.text = person.description
    }
}