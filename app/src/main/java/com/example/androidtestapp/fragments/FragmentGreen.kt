package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.*
import com.example.androidtestapp.helpers.Router
import kotlinx.android.synthetic.main.fragment_green.view.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class FragmentGreen : Fragment() , KoinComponent {
    private val router:Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_green, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_gr_go_to_red.setOnClickListener { router.showFragment(FragmentType.RedFragment) }
        view.button_gr_go_to_blue.setOnClickListener { router.showFragment(FragmentType.BlueFragment) }
    }
}