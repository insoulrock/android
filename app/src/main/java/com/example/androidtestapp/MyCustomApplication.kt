package com.example.androidtestapp

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.androidtestapp.helpers.applicationModule
import org.json.JSONObject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyCustomApplication : MultiDexApplication()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(applicationModule)
        }
    }
}