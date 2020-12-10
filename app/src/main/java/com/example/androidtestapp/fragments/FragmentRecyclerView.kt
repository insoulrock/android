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
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class FragmentRecyclerView : Fragment() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private val dataProvider: DataProvider by inject()
    private var disposableBag: CompositeDisposable = CompositeDisposable()
    private val tickerState: PublishSubject<List<TickerModel>> = PublishSubject.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataProvider.getTickers().subscribe(tickerState)

        recyclerAdapter = RecyclerAdapter(view.context)
//        val disposable = AndroidSchedulers.mainThread()
//            .schedulePeriodicallyDirect({ addDataSet() }, 0, 5, TimeUnit.SECONDS)
//        disposableBag.add(disposable)

        val rxViewDis = RxTextView.textChanges(textEditFilter)
            .debounce(500, TimeUnit.MILLISECONDS)
            .flatMap {
                tickerState
                    .map { tickersList ->
                    tickersList.filter { x ->
                        x.instrument?.startsWith(it) == true
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                recyclerAdapter.submitList(it)
                Log.d("123", it.toString())
            }, {
                Log.e("123", "", it)
            })

        disposableBag.add(rxViewDis)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
//        addDataSet()
        tickerState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    recyclerAdapter.submitList(it)
                    if (it.count() > 0) {
                        progressBar?.visibility = View.GONE
                    }
                }
            )
    }

    override fun onPause() {
        super.onPause()
        disposableBag.dispose()
    }

    private fun addDataSet() {
        Log.d("123", "${Thread.currentThread()} has run1.")
        var disposable = dataProvider.getTickers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recyclerAdapter.submitList(it)
                    if (it.count() > 0) {
                        progressBar?.visibility = View.GONE
                    }
                    Log.d("123", "addDataSet ${Thread.currentThread()}")
                },
                {
                    Log.d(tag, it.localizedMessage.toString())
                }
            )
        disposableBag.add(disposable)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = recyclerAdapter
        }
    }
}