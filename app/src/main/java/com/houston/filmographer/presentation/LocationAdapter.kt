package com.houston.filmographer.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.databinding.ItemLocationBinding
import com.houston.filmographer.domain.Location

class LocationAdapter(
    private val clickListener: LocationClickListener
): RecyclerView.Adapter<LocationViewHolder>() {

    private var locations = listOf<Location>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContent(locations: List<Location>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return LocationViewHolder(ItemLocationBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(
        holder: LocationViewHolder,
        position: Int
    ) {
        holder.bind(locations.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onLocationClick(locations.get(position))
        }
    }

    fun interface LocationClickListener {
        fun onLocationClick(location: Location)
    }
}
