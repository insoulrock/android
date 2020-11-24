package com.example.androidtestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.RecyclerAdapter
import com.example.androidtestapp.models.TickerModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class FragmentRecyclerView : Fragment() {
    private val URL:String = "https://api.crex24.com/v2/public/tickers"
    private var recyclerAdapter: RecyclerAdapter = RecyclerAdapter()
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private  lateinit var tickers: List<TickerModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val request: Request = Request.Builder().url(URL).build()
        val countDownLatch = CountDownLatch(1)
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) { countDownLatch.countDown() }

            override fun onResponse(call: Call?, response: Response?) {
                var json = response?.body()?.string()
                val gson = GsonBuilder().create()
                tickers = gson.fromJson(json,Array<TickerModel>::class.java).toList()
                recyclerAdapter.submitList(tickers)
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = recyclerAdapter
        }
    }
}