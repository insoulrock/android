package com.example.androidtestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtestapp.helpers.Router
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import java.util.*

class MainActivity : AppCompatActivity(), KoinComponent {
    private val LOG_TAG: String = "myLogs"
    private val EXTRA_KEY: String = "key1"
    private val router:Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router.putFragmentManager(supportFragmentManager)
        router.showFragment(FragmentType.MainFragment)
    }

    fun onClickOpenSecondActivityWindows(view: View)
    {
        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_KEY, "Hello!")
        startActivity(intent)
    }
    fun onClickOpenMaterial(view: View){
        router.showFragment((FragmentType.MaterialFragment))
    }

    fun onClickOpenRecycler(view: View)
    {
        router.showFragment(FragmentType.RecyclerFragment)
    }

    fun onClickOpenService(view: View)
    {
        router.showFragment(FragmentType.ServiceFragment)
    }

    fun onClickOpenNotifications(view: View)
    {
        router.showFragment(FragmentType.NotificationFragment)
    }
}