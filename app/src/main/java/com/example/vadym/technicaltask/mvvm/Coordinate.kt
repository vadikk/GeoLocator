package com.example.vadym.technicaltask.mvvm

class Coordinate() {

    var name: String? = null
    var x: Float? = null
    var y: Float? = null

    constructor(name: String, x: Float, y: Float) : this() {
        this.name = name
        this.x = x
        this.y = y
    }


}