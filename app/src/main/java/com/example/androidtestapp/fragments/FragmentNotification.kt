package com.example.androidtestapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.NewsNotifier
import kotlinx.android.synthetic.main.fragment_notification.*
import org.koin.core.component.KoinComponent

class FragmentNotification : Fragment(), KoinComponent {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_send_notification.setOnClickListener{
            NewsNotifier(context as Context)
                .sendNotification()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }
}