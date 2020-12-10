package com.example.androidtestapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.DataProvider
import com.example.androidtestapp.helpers.RecyclerAdapter
import com.example.androidtestapp.helpers.TickerCache
import com.example.androidtestapp.models.TickerModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class FragmentRecyclerView2 : Fragment() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private val dataProvider: DataProvider by inject()
    private val tickerCache: TickerCache by inject()
    private var disposableBag: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAdapter = RecyclerAdapter(view.context)
        initRecyclerView()

        val disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap {
                tickerCache.getTickers()
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("FragmentRecyclerView2", "getTickers")

                var tickers = it

                var filterText = textEditFilter.text.toString()
                if(!filterText.isNullOrEmpty()) {
                    tickers = tickers.filter { x -> x.instrument?.contains(filterText, true) == true }
                }

                if(switchSortDesc.isChecked){
                    tickers = tickers.sortedByDescending { t-> t.percentChange }
                }

                recyclerAdapter.submitList(tickers)
                if (it.count() > 0) {
                    progressBar?.visibility = View.GONE
                }
            }, {
                Log.e("123", it.localizedMessage, it)
            })

//        val disposable = Observable.interval(0, 2, TimeUnit.SECONDS)
//            .flatMap {
//                getTickersFiltered()
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                var tickers = it
//                if(switchSortDesc.isChecked)
//                {
//                    tickers = it.sortedByDescending { t->t.percentChange }
//                }
//                recyclerAdapter.submitList(tickers)
//                if (it.count() > 0) {
//                    progressBar?.visibility = View.GONE
//                }
//            }, {
//                Log.e("123", it.localizedMessage, it)
//            })
        disposableBag.add(disposable)
    }

    fun getTickersFiltered(): Observable<List<TickerModel>>{
        return dataProvider.getTickers()
            .subscribeOn(Schedulers.computation())
            .zipWith(
                RxTextView.textChanges(textEditFilter)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { it.toString() },
                BiFunction<List<TickerModel>, String, List<TickerModel>> { tickersList, filterText ->
                    tickersList.filter {
                        it.instrument?.startsWith(filterText.toUpperCase()) == true
                    }
                })
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onPause() {
        super.onPause()
        disposableBag.dispose()
    }


    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = recyclerAdapter
        }
    }
}