package com.example.androidtestapp.helpers

import com.example.androidtestapp.fragments.FragmentMain
import com.example.androidtestapp.fragments.FragmentBlue
import com.example.androidtestapp.fragments.FragmentGreen
import com.example.androidtestapp.fragments.FragmentRed
import org.koin.dsl.module.module

val applicationModule = module ( override = true ) {
    bean { FragmentMain() }
    bean { FragmentRed() }
    bean { FragmentGreen() }
    bean { FragmentBlue() }
    bean { Router() }
}