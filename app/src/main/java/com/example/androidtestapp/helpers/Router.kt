package com.example.androidtestapp.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidtestapp.*
import com.example.androidtestapp.fragments.FragmentBlue
import com.example.androidtestapp.fragments.FragmentGreen
import com.example.androidtestapp.fragments.FragmentRed
import org.koin.core.component.KoinComponent

class Router: KoinComponent
{
    private lateinit var fragmentManager: FragmentManager

    fun putFragmentManager(fragmentManager: FragmentManager)
    {
        this.fragmentManager = fragmentManager
    }

    fun showFragment(fragmentType: FragmentType)
    {
        when(fragmentType)
        {
            is FragmentType.BlueFragment -> openFragment(FragmentBlue())
            is FragmentType.GreenFragment -> openFragment(FragmentGreen())
            is FragmentType.RedFragment -> openFragment(FragmentRed())
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