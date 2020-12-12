package com.example.androidtestapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.TickerService
import kotlinx.android.synthetic.main.fragment_service.*
import org.koin.core.component.KoinComponent

class FragmentService : Fragment(), KoinComponent {
    private val LOG_TAG = "FragmentService"

    //Пуш уведомления, запрашивая текущее время
    //Каждые 30 сек показывать пуши с этим временем в секундах
    //ЧЧ-ММ-СС
    //Открывать экран с пуш уведомлением

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStartService.setOnClickListener { onClickStartService(view) }
        buttonStopService.setOnClickListener { onClickStopService(view) }
    }
    fun onClickStartService(view: View){
        Log.d(LOG_TAG, "onClickStartService - startService")
        context?.startService(Intent(context, TickerService::class.java))
    }

    fun onClickStopService(view: View){
        Log.d(LOG_TAG, "onClickStartService - stopService")
        context?.stopService(Intent(context, TickerService::class.java))
    }
}