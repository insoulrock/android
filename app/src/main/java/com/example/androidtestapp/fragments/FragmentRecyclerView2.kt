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
import com.example.androidtestapp.helpers.TickerState
import com.example.androidtestapp.models.TickerModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class FragmentRecyclerView2 : Fragment() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private val dataProvider: DataProvider by inject()
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
        val disposable = Observable.interval(0, 5, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .flatMap {
                dataProvider.getTickers()
            }
            .observeOn(Schedulers.computation())
            .zipWith(
                RxTextView.textChanges(textEditSearch).map { it.toString() },
                BiFunction<List<TickerModel>, String, List<TickerModel>> { tickersList, filterText ->
                    tickersList.filter { it.instrument?.startsWith(filterText.toUpperCase()) == true }
                })
                //Сделать сортировку по названию тикета и изменению %
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                recyclerAdapter.submitList(it)
                if (it.count() > 0) {
                    recycler_view_loading?.visibility = View.GONE
                }
            }, {
                Log.e("123", it.localizedMessage, it)
            })
        disposableBag.add(disposable)
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