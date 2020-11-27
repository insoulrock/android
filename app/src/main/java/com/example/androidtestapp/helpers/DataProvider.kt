package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataProvider : KoinComponent {
    private val URL_TICKERS:String = "https://api.crex24.com/v2/public/tickers"
    private var tickers:List<TickerModel> = ArrayList()
    private val httpRequester:HttpRequester by inject()

    fun getTickers() : List<TickerModel>
    {
        var respJson = httpRequester.Get(URL_TICKERS)
        if(respJson.isNullOrEmpty()) return listOf(TickerModel())

        tickers = JsonConverter.Convert(respJson)
//        val gson = GsonBuilder().create()
//        tickers = gson.fromJson(respJson, object: TypeToken<ArrayList<TickerModel>>() {}.type)
////        tickers = JsonConverter().Convert(respJson, TypeToken<List<TickerModel>>())
//
//        var tickersConverted = GsonBuilder().create().fromJson(respJson, Array<TickerModel>::class.java)
//        if(tickersConverted != null) tickers = tickersConverted;

        return tickers;
    }
}