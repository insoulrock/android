package com.example.androidtestapp.helpers

import android.util.Log
import io.reactivex.Observable
import org.koin.core.component.KoinComponent

class RecyclerViewHelper : KoinComponent {

    fun startListen(textFilter: Observable<String>, switchSort: Observable<Boolean>){

        textFilter
            .subscribe({
                Log.d("123", "!!! - $it")
            })

        switchSort
            .subscribe({
                Log.d("123", "!!! - $it")
            })
    }
}