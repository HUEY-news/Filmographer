package com.houston.filmographer.presentation.cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.houston.filmographer.databinding.ItemMovieCastHeaderBinding
import com.houston.filmographer.databinding.ItemMovieCastPersonBinding

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - передать делегаты в конструкторе класса ListDelegationAdapter

fun movieCastHeaderDelegate() =
    adapterDelegateViewBinding<MovieCastItem.HeaderItem, ViewItem, ItemMovieCastHeaderBinding>(
        { layoutInflater, root -> ItemMovieCastHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.textViewHeader.text = item.header
        }
    }

fun movieCastPersonDelegate() =
    adapterDelegateViewBinding<MovieCastItem.PersonItem, ViewItem, ItemMovieCastPersonBinding>(
        { layoutInflater, root -> ItemMovieCastPersonBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
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
