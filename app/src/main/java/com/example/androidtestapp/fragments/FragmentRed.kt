package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.FragmentType
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_red.view.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class FragmentRed : Fragment(), KoinComponent {
    private val router:Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_red, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_rd_go_to_blue.setOnClickListener { router.showFragment(FragmentType.BlueFragment) }
        view.button_rd_go_to_green.setOnClickListener { router.showFragment(FragmentType.GreenFragment) }
    }
}