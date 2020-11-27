package com.example.androidtestapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestapp.R
import org.koin.core.component.KoinComponent

class FragmentMenu : Fragment(), KoinComponent
{
    private val LOG_TAG: String  = "myLogs";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "FragmentMain onCreate");
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "FragmentMain onCreateView");
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }
}