package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_red.view.*
import org.koin.android.ext.android.inject

class FragmentRed : ColorFragment() {
    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_red, container, false)
        view.button_rd_go_to_blue.setOnClickListener { openFragment(router.getBlueFragment())}
        view.button_rd_go_to_green.setOnClickListener { openFragment(router.getGreenFragment())}
        return view;
    }
}