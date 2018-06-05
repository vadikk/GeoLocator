package com.example.vadym.technicaltask.mvvm

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.LinearLayoutManager
import com.example.vadym.technicaltask.recycler.ListRecyclerAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class ViewModel @Inject constructor(var model: Model,
                                    var layoutManager: LinearLayoutManager,
                                    var adapter: ListRecyclerAdapter) : ViewModel() {

    private val map = mutableListOf<Coordinate>()
    lateinit var mMap: GoogleMap

    fun downloadData() {
        model.getDatabaseReference().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    p0.children.mapNotNullTo(map) {
                        it.getValue(Coordinate::class.java)
                    }
                    adapter.addAll(map)

                    for (coordinate: Coordinate in map) {
                        val pos = LatLng((coordinate.x)!!.toDouble(), (coordinate.y)!!.toDouble())
                        mMap.addMarker(MarkerOptions().position(pos).title(coordinate.name))
                    }
                }
            }
        })
    }

    fun addToList(coordinate: Coordinate) {

        val key = model.getDatabaseReference().push().key
        model.getDatabaseReference().child(key!!).setValue(coordinate)
        adapter.addToList(coordinate)

        showCurrentUserPosition(coordinate)
    }

    fun showCurrentUserPosition(coordinate: Coordinate) {
        val pos = LatLng((coordinate.x)!!.toDouble(), (coordinate.y)!!.toDouble())
        mMap.addMarker(MarkerOptions().position(pos).title(coordinate.name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 6f))
    }

    fun getMap(p0: GoogleMap) {
        mMap = p0

    }
}