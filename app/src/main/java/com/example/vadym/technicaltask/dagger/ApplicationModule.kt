package com.example.vadym.technicaltask.dagger

import android.app.Application
import android.support.v7.widget.LinearLayoutManager
import com.example.vadym.technicaltask.mvvm.Model
import com.example.vadym.technicaltask.mvvm.ViewModel
import com.example.vadym.technicaltask.recycler.ListRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun getDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("users")
    }

    @Provides
    fun getModel(databaseReference: DatabaseReference): Model {
        return Model(databaseReference)
    }

    @Provides
    fun getLayoutManager(app: Application): LinearLayoutManager {
        val layoutManager = LinearLayoutManager(app)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        return layoutManager
    }

    @Provides
    fun getViewModel(model: Model, layoutManager: LinearLayoutManager, listRecyclerAdapter: ListRecyclerAdapter): ViewModel {
        return ViewModel(model, layoutManager, listRecyclerAdapter)
    }
}