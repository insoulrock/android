package com.example.androidtestapp.helpers

import android.util.Log
import com.example.androidtestapp.models.TickerModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class RecyclerViewHelper : KoinComponent {
    private val tickerCache: TickerCache by inject()
    private var filter:String = ""
    private lateinit var editTextFilter:TextInputEditText
    private lateinit var switchSort: SwitchMaterial
    private var needSortByPercent:Boolean = false
    private var tickers: List<TickerModel> = ArrayList<TickerModel>()

    fun getData(): Observable<List<TickerModel>>{
        var tickersObs = Observable.interval(1000,5000, TimeUnit.MILLISECONDS)

        var filterObs = RxTextView.textChanges(editTextFilter)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                Log.d("RecyclerViewHelper", "filter = $it.toString() ")
                filter = it.toString()
            }

        var sortObs = RxView.clicks(switchSort)
            .map {
                Log.d("RecyclerViewHelper", "percentSort = $switchSort.isChecked")
                needSortByPercent = switchSort.isChecked
            }
            .subscribeOn(AndroidSchedulers.mainThread())


        return Observable.merge(sortObs, tickersObs, filterObs)
            .flatMap {
                tickerCache.getTickers()
            }
            .map {
                tickers = it

                if(needSortByPercent)
                    tickers = it.sortedByDescending { x->x.percentChange }
                if(!filter.isNullOrEmpty())
                    tickers = it.filter { x -> x.instrument?.contains(filter, true) == true }
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