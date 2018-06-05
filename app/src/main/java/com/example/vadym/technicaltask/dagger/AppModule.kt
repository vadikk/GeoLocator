package com.example.vadym.technicaltask.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var app: Application) {

    @Provides
    @Singleton
    fun getAppAplication(): Application {
        return app
    }
}