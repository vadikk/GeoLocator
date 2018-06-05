package com.example.vadym.technicaltask.mvvm

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.vadym.technicaltask.recycler.ListRecyclerAdapter

@BindingAdapter("items")
fun RecyclerView.setItems(listRecyclerAdapter: ListRecyclerAdapter?){
    this.adapter = listRecyclerAdapter
}

@BindingAdapter("manager")
fun RecyclerView.setManager(layoutManager: LinearLayoutManager?){
    this.layoutManager = layoutManager
}