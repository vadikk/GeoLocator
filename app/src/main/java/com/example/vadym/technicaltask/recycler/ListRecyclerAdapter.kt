package com.example.vadym.technicaltask.recycler

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vadym.technicaltask.R
import com.example.vadym.technicaltask.databinding.CoordinateLayoutBinding
import com.example.vadym.technicaltask.mvvm.Coordinate
import javax.inject.Inject

class ListRecyclerAdapter @Inject constructor() : RecyclerView.Adapter<ListViewHolder>() {

    private var list = mutableListOf<Coordinate>()

    fun addToList(coordinate: Coordinate) {
        list.add(coordinate)
        notifyItemInserted(itemCount - 1)
    }

    fun addAll(list: List<Coordinate>) {
        var startPos = itemCount - 1;
        notifyItemRangeInserted(startPos, list.size)
        this.list.addAll(list)
    }

    fun getCoordinate(position: Int): Coordinate? {
        if (position < 0 && position >= itemCount) return null

        return list.get(position)
    }

    fun getAdapter(): List<Coordinate> = list

    fun clearItemsAdapter() {
        notifyItemRangeRemoved(0, itemCount)
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(layoutInflater, R.layout.coordinate_layout, parent, false) as CoordinateLayoutBinding
        return ListViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val coordinate = list.get(holder.adapterPosition)
        holder.setBinding(coordinate)
    }
}