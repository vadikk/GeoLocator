package com.example.vadym.technicaltask.mvvm

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class Model() {

    private lateinit var databaseReference: DatabaseReference

    @Inject
    constructor(databaseReference: DatabaseReference) : this() {
        this.databaseReference = databaseReference
    }

    fun getDatabaseReference(): DatabaseReference {
        return databaseReference
    }
}