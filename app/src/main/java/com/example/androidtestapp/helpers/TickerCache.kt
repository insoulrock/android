package com.example.androidtestapp.helpers

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.example.GetTickersQuery
import com.example.androidtestapp.models.TickerModel
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TickerCache : KoinComponent, Disposable {
    private var tickers: List<TickerModel> = ArrayList()
    private var disBag: CompositeDisposable = CompositeDisposable()

    init {
        Log.d("TickerCache", "-> init")
        startUpdateTickers()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private fun startUpdateTickers() {
        disBag.add(Observable.interval(0, 20, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                Log.d("TickerCache", "-> getTickers")

                ApolloClient.builder()
                    .serverUrl("https://develop-webx-crex24.moontkn.com/graphql-mobile")
                    .build()
                    .rxQuery(GetTickersQuery())
            }
            .observeOn(Schedulers.computation())
            .map {
                it.data?.market?.tickers?.map {
                    TickerModel(it)
                } ?: listOf()
            }
            .observeOn(Schedulers.io())
            .subscribe{
                tickers = it
            })
    }

    override fun dispose() {
        disBag.clear()
    }

    override fun isDisposed(): Boolean {
        return disBag.size() == 0
    }

    fun getTickers():Observable<List<TickerModel>> {
        return Observable.just(tickers)
    }
}
