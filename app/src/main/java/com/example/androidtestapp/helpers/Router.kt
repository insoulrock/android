package com.example.androidtestapp.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidtestapp.FragmentEnum
import com.example.androidtestapp.R
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

    fun showFragment(fragmentEnum: FragmentEnum)
    {
        when(fragmentEnum)
        {
            FragmentEnum.BLUE_FRAGMENT -> openFragment(blueFragment)
            FragmentEnum.GREEN_FRAGMENT -> openFragment(greenFragment)
            FragmentEnum.RED_FRAGMENT -> openFragment(redFragment)
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