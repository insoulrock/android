package com.example.androidtestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidtestapp.fragments.FragmentGreen
import com.example.androidtestapp.helpers.Router
import com.example.androidtestapp.helpers.applicationModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class MainActivity : AppCompatActivity() {
    private val LOG_TAG: String = "myLogs";
    private val EXTRA_KEY: String = "key1";
    private val router:Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "MainActivity onCreate");
        startKoin(this, listOf(applicationModule))
        router.putFragmentManager(supportFragmentManager)
    }

    fun onClickOpenActivityWindows(view: View)
    {
        Log.d(LOG_TAG, "MainActivity onClickOpenActivityWindows")
        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_KEY, "Hello!")
        startActivity(intent)
    }

    fun openMainWindow(view: View)
    {
        router.showFragment(GreenFragment)
    }
}