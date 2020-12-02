package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataProvider : KoinComponent {
    private val URL_TICKERS:String = "https://api.crex24.com/v2/public/tickers"
    private val httpRequester:HttpRequester by inject()

    fun getTickers() : Single<List<TickerModel>>
    {
        var observable = httpRequester.Get(URL_TICKERS)
        return observable
            .subscribeOn(Schedulers.io())
            .map { data -> JsonConverter.Convert<List<TickerModel>>(data) }
    }
}