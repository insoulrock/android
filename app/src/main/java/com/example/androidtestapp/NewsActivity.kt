package com.example.androidtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.androidtestapp.helpers.NewsNotifier

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        var notificationId = intent.getIntExtra("notificationId", 0)
        var notificationTitle = intent.getStringExtra("notificationTitle")
        var notificationText = intent.getStringExtra("notificationText")
        NewsNotifier(this)
            .cancelNotification(notificationId)

        var textView = findViewById<TextView>(R.id.textview_news)

        textView.text = "NotificationId: $notificationId\nNotificationTitle:\n$notificationTitle\nNotificationText:\n$notificationText"
    }

}