package com.example.vadym.technicaltask.dagger

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent

        fun get(context: Context): Application {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .applicationModule(ApplicationModule())
                .build()
    }

}