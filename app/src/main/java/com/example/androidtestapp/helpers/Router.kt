package com.example.androidtestapp.helpers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidtestapp.R
import com.example.androidtestapp.fragments.ColorFragment
import com.example.androidtestapp.fragments.FragmentBlue
import com.example.androidtestapp.fragments.FragmentGreen
import com.example.androidtestapp.fragments.FragmentRed
import org.koin.android.ext.android.inject

class Router: ColorFragment()//Не понял от какого класса унаследоваться, чтобы инжект был доступен...
{
    private val greenFragment by inject<FragmentGreen>()
    private val redFragment by inject<FragmentRed>()
    private val blueFragment by inject<FragmentBlue>()

    fun getGreenFragment(): Fragment
    {
        openFragment(greenFragment)
        return greenFragment
    }

    fun getRedFragment(): Fragment
    {
        return  redFragment
    }

    fun getBlueFragment(): Fragment
    {
        return  blueFragment
    }
}