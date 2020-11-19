package com.example.androidtestapp

sealed class FragmentType
{
    object GreenFragment: FragmentType()
    object RedFragment: FragmentType()
    object BlueFragment: FragmentType()
}
