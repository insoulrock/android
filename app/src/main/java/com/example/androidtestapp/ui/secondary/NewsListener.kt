package com.example.androidtestapp.ui.secondary

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.functions.Consumer

class NewsListener(
    private val context: Context
) : Consumer<FeatureOne.News> {

    override fun accept(news: FeatureOne.News) {
        when (news) {
            is FeatureOne.News.ToastOnClick -> sendToast(news.message)
        }
    }

    private fun sendToast(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Log.d("123", message)
    }
}