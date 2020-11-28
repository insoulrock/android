package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataProvider : KoinComponent {
    private val URL_TICKERS:String = "https://api.crex24.com/v2/public/tickers"
    private var tickers:List<TickerModel> = ArrayList()
    private val httpRequester:HttpRequester by inject()

    fun getTickers() : Observable<List<TickerModel>>
    {
        var observable = httpRequester.Get(URL_TICKERS)
        return observable.map { data ->
            JsonConverter.Convert<List<TickerModel>>(data)
        }
    }
}