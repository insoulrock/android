package com.example.androidtestapp.helpers

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class TickerService : Service() {
    private val LOG_TAG = "TickerService"
    private var items: List<Long> = ArrayList<Long>()

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "TickerService OnCreate!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "TickerService OnDestroy!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "TickerService OnStartCommand! startId:$startId, flags:$flags")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(LOG_TAG, "TickerService OnBind!")
        return null
    }
}