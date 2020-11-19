package com.example.androidtestapp.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidtestapp.*
import com.example.androidtestapp.fragments.FragmentBlue
import com.example.androidtestapp.fragments.FragmentGreen
import com.example.androidtestapp.fragments.FragmentRed
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Router: KoinComponent
{
    private val greenFragment:FragmentGreen by inject()
    private val redFragment:FragmentRed by inject()
    private val blueFragment:FragmentBlue by inject()
    private lateinit var fragmentManager: FragmentManager

    fun putFragmentManager(fragmentManager: FragmentManager)
    {
        this.fragmentManager = fragmentManager
    }

    fun showFragment(fragmentType: FragmentType)
    {
        when(fragmentType)
        {
            BlueFragment -> openFragment(blueFragment)
            GreenFragment -> openFragment(greenFragment)
            RedFragment -> openFragment(redFragment)
        }
    }

    private fun openFragment(fragment: Fragment)
    {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit() }
    }
}