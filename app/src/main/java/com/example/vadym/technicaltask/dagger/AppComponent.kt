package com.example.vadym.technicaltask.dagger

import com.example.vadym.technicaltask.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApplicationModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)

}