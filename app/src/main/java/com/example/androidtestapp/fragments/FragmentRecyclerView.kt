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
import com.example.androidtestapp.helpers.JsonConverter
import com.example.androidtestapp.helpers.RecyclerAdapter
import com.example.androidtestapp.models.TickerModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import okhttp3.Request
import org.koin.android.ext.android.inject
import java.util.HashMap
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class FragmentRecyclerView : Fragment() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private val dataProvider: DataProvider by inject()
    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAdapter = RecyclerAdapter(view.context)
        disposable = AndroidSchedulers.mainThread().schedulePeriodicallyDirect({
            recycler_view.visibility =
                if (recycler_view.visibility == View.GONE) View.VISIBLE else View.GONE
        }, 0, 2, TimeUnit.SECONDS)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun addDataSet() {
        thread {
            Log.d("123", "${Thread.currentThread()} has run1.")
            var disposable = Observable.fromCallable  {
                try {
                    var request: Request = Request.Builder().url(url).build()
                    var resp = client.newCall(request).execute()
                    return@fromCallable resp?.body()?.string()
                }.map { JsonConverter.Convert<List<TickerModel>>(data) }.observeOn(AndroidSchedulers.mainThread()).subscribe {
                // main thread
                    recyclerAdapter.submitList(it)
            }



            AndroidSchedulers.mainThread().scheduleDirect({
                recyclerAdapter.submitList(tickers)
            }, 20, TimeUnit.SECONDS)
            Log.d("123", "${Thread.currentThread()} has run2.")
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = recyclerAdapter
        }
    }
}