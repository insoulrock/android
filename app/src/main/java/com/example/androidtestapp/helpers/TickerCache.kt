package com.example.androidtestapp.helpers

import android.util.Log
import com.example.androidtestapp.models.TickerModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class TickerCache : KoinComponent, Disposable {
    private val URL_TICKERS: String = "https://api.crex24.com/v2/public/tickers"
    private val httpRequester: HttpRequester by inject()
    private var tickers: List<TickerModel> = ArrayList()//publishSubject
    private var disBag: CompositeDisposable = CompositeDisposable()

    init {
        Log.d("TickerCache", "-> init")
        startUpdateTickers()
    }

    private fun startUpdateTickers() {
        disBag.add(Observable.interval(0, 20, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap {
                Log.d("TickerCache", "-> getTickers")
                httpRequester.get(URL_TICKERS)
                    .subscribeOn(Schedulers.io())
                    .map { data ->
                        JsonConverter.Convert<List<TickerModel>>(data)
                    }
            }
            .subscribe({
                tickers = it
            }))
    }

    override fun dispose() {
        disBag.clear()
    }

    override fun isDisposed(): Boolean {
        return disBag.size() == 0
    }

    fun getTickers() = Observable.just(tickers)
}
