package com.example.androidtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

private  val LOG_TAG: String = "myLogs";
private val EXTRA_KEY: String = "key1";

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var extraValue = intent.getStringExtra(EXTRA_KEY)
        Log.d(LOG_TAG, "SecondActivity onCreate $extraValue")
        var textView = findViewById<TextView>(R.id.textViewMain)
        textView.text = extraValue
    }
}
