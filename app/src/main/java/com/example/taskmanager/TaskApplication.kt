package com.example.taskmanager

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
        FirebaseApp.initializeApp(this)
    }
}