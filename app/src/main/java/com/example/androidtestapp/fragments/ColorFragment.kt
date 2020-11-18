package com.example.androidtestapp.fragments

import androidx.fragment.app.Fragment
import com.example.androidtestapp.R

open class ColorFragment: Fragment()
{
    fun openFragment(fragment: Fragment)
    {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit() }
    }
}