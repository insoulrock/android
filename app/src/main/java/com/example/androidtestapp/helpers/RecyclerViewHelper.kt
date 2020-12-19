package com.example.androidtestapp.helpers

import android.util.Log
import com.example.androidtestapp.models.TickerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class RecyclerViewHelper : KoinComponent {
    private val tickerCache: TickerCache by inject()
    private var filter:String = ""
    private var needSortByPercent:Boolean = false
    private lateinit var filterObs: Observable<String>
    private lateinit var sortObs: Observable<Boolean>

    fun getData(): Observable<List<TickerModel>>{
        var intervalObs = Observable.interval(1000,10000, TimeUnit.MILLISECONDS)

        return Observable.merge(sortObs, intervalObs, filterObs)
            .flatMap {
                if(it is String)
                {
                    filter = it
                }
                if (it is Boolean){
                    needSortByPercent = it
                }
                tickerCache.getTickers()
            }
            .map {
                var tickers = it

                if(!filter.isNullOrEmpty())
                    tickers = tickers.filter { x -> x.instrument?.contains(filter, true) == true }

                if(needSortByPercent)
                    tickers = tickers.sortedByDescending { x->x.percentChange }

                Log.d("RecyclerViewHelper", "merge: tickerCount = ${tickers.count()}, filter=$filter")
                tickers
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun startListen(filterObs: Observable<String>, switchObs: Observable<Boolean>){
        this.filterObs = filterObs
        this.sortObs = switchObs
    }
}