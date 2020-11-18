package com.example.androidtestapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private val LOG_TAG: String  = "myLogs";

class FragmentBlue : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "FragmentBlue onCreate");
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "FragmentBlue onCreateView");
        return inflater.inflate(R.layout.fragment_blue, container, false)
    }
}