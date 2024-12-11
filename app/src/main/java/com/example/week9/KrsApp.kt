package com.example.week9

import android.app.Application
import com.example.week9.dependenciesInjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // membuat instance
        // instance adalah object yang dibuat dari class
    }
}