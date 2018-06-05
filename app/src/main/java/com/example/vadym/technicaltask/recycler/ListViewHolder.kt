package com.example.vadym.technicaltask.recycler

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.vadym.technicaltask.BR
import com.example.vadym.technicaltask.databinding.CoordinateLayoutBinding
import com.example.vadym.technicaltask.mvvm.Coordinate

class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var coordinateBinding: CoordinateLayoutBinding? = null

    init {
        coordinateBinding = DataBindingUtil.bind(itemView!!)
    }

    fun setBinding(coordinate: Coordinate) {
        coordinateBinding!!.setVariable(BR.coordinate, coordinate)
    }
}