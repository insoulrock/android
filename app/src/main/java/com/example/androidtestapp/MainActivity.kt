package com.example.androidtestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtestapp.helpers.Router
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    private val LOG_TAG: String = "myLogs";
    private val EXTRA_KEY: String = "key1";
    private val router:Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "MainActivity onCreate");
        router.putFragmentManager(supportFragmentManager)
    }

    fun onClickOpenActivityWindows(view: View)
    {
        Log.d(LOG_TAG, "MainActivity onClickOpenActivityWindows")
        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_KEY, "Hello!")
        startActivity(intent)
    }
    fun onClickOpenMaterial(view: View){
        Log.d(LOG_TAG, "MainActivity onClickOpenMaterial")
        router.showFragment((FragmentType.MaterialFragment))
    }

    fun onClickOpenRecycler(view: View)
    {
        router.showFragment(FragmentType.RecyclerFragment)
    }

    fun openMainWindow(view: View)
    {
        router.showFragment(FragmentType.GreenFragment)
    }
}