package com.example.androidtestapp.helpers

import android.util.Log
import com.example.androidtestapp.models.TickerModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class TickerCache : KoinComponent {
    private val dataProvider: DataProvider by inject()
    private var tickers:List<TickerModel> = ArrayList()

    init {
        Log.d("TickerCache", "-> init")
        startUpdateTickers()
    }

    private fun startUpdateTickers() {
        Observable.interval(0, 20, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap {
                Log.d("TickerCache", "-> getTickers")
                dataProvider.getTickers()
            }
            .subscribe{
               tickers = it
            }
    }

    fun getTickers() = Observable.just(tickers)
}
