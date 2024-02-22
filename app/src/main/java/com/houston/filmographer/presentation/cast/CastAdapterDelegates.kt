package com.houston.filmographer.presentation.cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.houston.filmographer.databinding.ItemHeaderBinding
import com.houston.filmographer.databinding.ItemPersonBinding

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - передать делегаты в конструкторе класса ListDelegationAdapter

fun movieCastHeaderDelegate() =
    adapterDelegateViewBinding<CastItem.HeaderItem, ViewItem, ItemHeaderBinding>(
        { layoutInflater, root -> ItemHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.textViewHeader.text = item.header
        }
    }

fun movieCastPersonDelegate() =
    adapterDelegateViewBinding<CastItem.PersonItem, ViewItem, ItemPersonBinding>(
        { layoutInflater, root -> ItemPersonBinding.inflate(layoutInflater, root, false) }
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
