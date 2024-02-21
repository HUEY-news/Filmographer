package com.houston.filmographer.presentation.cast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ItemHeaderBinding
import com.houston.filmographer.databinding.ItemPersonBinding

class CastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<CastItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<CastItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        when (items.get(position)) {
            is CastItem.HeaderItem -> return R.layout.item_header
            is CastItem.PersonItem -> return R.layout.item_person
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val headerBinding = ItemHeaderBinding.inflate(layoutInspector, parent, false)
        val personBinding = ItemPersonBinding.inflate(layoutInspector, parent, false)

        when (viewType) {
            R.layout.item_header -> return HeaderViewHolder(headerBinding)
            R.layout.item_person -> return PersonViewHolder(personBinding)
            else -> error("НЕИЗВЕСТНЫЙ VIEW TYPE [$viewType]")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            R.layout.item_header -> {
                val headerHolder = holder as HeaderViewHolder
                val headerItem = items.get(position) as CastItem.HeaderItem
                headerHolder.bind(headerItem)
            }

            R.layout.item_person -> {
                val personHolder = holder as PersonViewHolder
                val personItem = items.get(position) as CastItem.PersonItem
                personHolder.bind(personItem)
            }
        }
    }
}