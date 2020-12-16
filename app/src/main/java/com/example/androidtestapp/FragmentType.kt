package com.example.androidtestapp

sealed class FragmentType
{
    object GreenFragment: FragmentType()
    object RedFragment: FragmentType()
    object BlueFragment: FragmentType()
    object MaterialFragment: FragmentType()
    object RecyclerFragment: FragmentType()
    object MainFragment: FragmentType()
    object ServiceFragment: FragmentType()
    object NotificationFragment: FragmentType()
}
