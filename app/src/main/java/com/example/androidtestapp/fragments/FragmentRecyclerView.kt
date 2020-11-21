package com.example.androidtestapp.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapp.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


//https://api.crex24.com/v2/public/tickers

class FragmentRecyclerView : Fragment() {

    private val URL:String = "https://api.crex24.com/v2/public/tickers"
    private var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                var json = response?.body()?.string()
                var jsonObject = JSONObject(json)

            }
        })



        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }
}