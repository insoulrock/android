package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataProvider : KoinComponent {
    private val tickerCache:TickerCache by inject()

    fun getTickers() : Observable<List<TickerModel>>
    {
        return tickerCache.getTickers()
    }
}