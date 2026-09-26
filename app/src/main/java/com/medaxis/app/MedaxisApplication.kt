package com.medaxis.app

import android.app.Application

class MedaxisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MedaxisApplication
            private set
    }
}
