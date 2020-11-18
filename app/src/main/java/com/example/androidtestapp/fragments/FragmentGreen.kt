package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_green.view.*
import org.koin.android.ext.android.inject

class FragmentGreen : ColorFragment() {

    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_green, container, false)
        view.button_gr_go_to_red.setOnClickListener { openFragment(router.getRedFragment()) }
        view.button_gr_go_to_blue.setOnClickListener { openFragment(router.getBlueFragment())}
        return  view;
    }
}