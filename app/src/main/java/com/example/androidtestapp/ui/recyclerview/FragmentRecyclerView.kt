package com.example.androidtestapp.ui.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.RecyclerAdapter
import com.example.androidtestapp.helpers.RecyclerViewHelper
import com.example.androidtestapp.ui.common.ObservableSourceFragment
import com.example.androidtestapp.ui.recyclerview.events.UiEvent
import com.example.androidtestapp.ui.recyclerview.viewmodel.ViewModel
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import java.util.concurrent.TimeUnit

class FragmentRecyclerView : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var disBag: CompositeDisposable = CompositeDisposable()
    private val LOG_TAG = FragmentRecyclerView::class.java.simpleName
    private lateinit var bindings: RecyclerViewBindings
    private var filter: String = ""
    private var needSort: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun accept(t: ViewModel) {
        progressBar?.visibility = if(t.isLoading) View.VISIBLE else View.GONE
        Log.d(LOG_TAG, "isLoading = ${t.isLoading}")
        recyclerAdapter?.submitList(t.tickers)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAdapter = RecyclerAdapter(view.context)
        initRecyclerView()

        bindings = RecyclerViewBindings(this, FeatureRecyclerView(), NewsListener(context))
        bindings.setup(this)

        disBag.add(Observable.merge(
            RxTextView.textChanges(textEditFilter)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { filter = it.toString() },
            Observable.interval(1000,10000, TimeUnit.MILLISECONDS),
            RxView.clicks(switchSortDesc)
                .map { needSort = switchSortDesc.isChecked }
                .subscribeOn(AndroidSchedulers.mainThread()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ onNext(UiEvent.RefreshList(filter, needSort)) })
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
        disBag.dispose()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = recyclerAdapter
        }
    }
}