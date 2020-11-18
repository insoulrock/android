package com.example.androidtestapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

private  val LOG_TAG: String = "myLogs";

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "MainActivity onCreate");

        addToFragmentManager(FragmentMain())
    }

    fun openGreenWindow(view: View)
    {
        addToFragmentManager(FragmentGreen())
    }

    fun openRedWindow(view: View)
    {
        addToFragmentManager(FragmentRed())
    }

    fun openBlueWindow(view: View)
    {
        addToFragmentManager(FragmentBlue())
    }

    fun addToFragmentManager(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}