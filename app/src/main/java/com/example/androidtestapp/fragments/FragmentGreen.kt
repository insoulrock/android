package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.FragmentEnum
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_green.view.*
import org.koin.android.ext.android.inject

class FragmentGreen : Fragment() {
    private val router:Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_green, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_gr_go_to_red.setOnClickListener { router.showFragment(FragmentEnum.RED_FRAGMENT) }
        view.button_gr_go_to_blue.setOnClickListener { router.showFragment(FragmentEnum.BLUE_FRAGMENT) }
    }
}