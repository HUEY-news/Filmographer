package com.houston.filmographer.presentation.cast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemCastBinding
import com.houston.filmographer.domain.model.Person

class CastAdapter: RecyclerView.Adapter<CastViewHolder>() {

    private var persons = listOf<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(layoutInspector, parent, false)
        return CastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(persons.get(position))
    }
}