package com.houston.filmographer.presentation.name

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemPersonBinding
import com.houston.filmographer.domain.search.model.Person

class PersonAdapter : RecyclerView.Adapter<PersonViewHolder>() {

    private var items = listOf<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<Person>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(layoutInspector, parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(items.get(position))
    }
}