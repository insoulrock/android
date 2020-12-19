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
import com.example.androidtestapp.helpers.RecyclerViewHelper
import com.example.androidtestapp.helpers.TickerCache
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class FragmentRecyclerView : Fragment() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var disBag: CompositeDisposable = CompositeDisposable()
    private val LOG_TAG = FragmentRecyclerView::class.java.simpleName
    private val subscriber: RecyclerViewHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber.startListen(
            RxTextView.textChanges(textEditFilter)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map {
                    Log.d("RecyclerViewHelper", "filter = $it.toString() ")
                    it.toString()
                }
            ,
            RxView.clicks(switchSortDesc)
                .map {
                    Log.d("RecyclerViewHelper", "percentSort = $switchSortDesc.isChecked")
                    switchSortDesc.isChecked
                }
                .subscribeOn(AndroidSchedulers.mainThread())
        )
        recyclerAdapter = RecyclerAdapter(view.context)
        initRecyclerView()
        progressBar?.visibility = View.GONE
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