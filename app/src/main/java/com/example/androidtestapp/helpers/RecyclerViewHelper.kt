package com.example.androidtestapp.helpers

import android.util.Log
import com.example.androidtestapp.models.TickerModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class RecyclerViewHelper : KoinComponent {
    private val tickerCache: TickerCache by inject()
    private var filter:String = ""
    private var needSortByPercent:Boolean = false
    private lateinit var editTextFilter:TextInputEditText
    private lateinit var switchSort: SwitchMaterial

    fun getData(): Observable<List<TickerModel>>{
        var tickersObs = Observable.interval(1000,10000, TimeUnit.MILLISECONDS)

        var filterObs = RxTextView.textChanges(editTextFilter)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                Log.d("RecyclerViewHelper", "filter = $it.toString() ")
                filter = it.toString()
                it.toString()
            }

        var sortObs = RxView.clicks(switchSort)
            .map {
                Log.d("RecyclerViewHelper", "percentSort = $switchSort.isChecked")
                needSortByPercent = switchSort.isChecked
                switchSort.isChecked
            }
            .subscribeOn(AndroidSchedulers.mainThread())

        return Observable.merge(sortObs, tickersObs, filterObs)
            .flatMap {
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

    fun startListen(editTextFilter: TextInputEditText, switchSort: SwitchMaterial){
        this.editTextFilter = editTextFilter
        this.switchSort = switchSort
    }
}