package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.BlueFragment
import com.example.androidtestapp.GreenFragment
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_red.view.*
import org.koin.android.ext.android.inject

class FragmentRed : Fragment() {
    private val router:Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_red, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_rd_go_to_blue.setOnClickListener { router.showFragment(BlueFragment) }
        view.button_rd_go_to_green.setOnClickListener { router.showFragment(GreenFragment) }
    }
}