package com.example.androidtestapp

import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.core.app.NotificationManagerCompat
import com.example.androidtestapp.fragments.HelpFragment
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*
import kotlin.random.Random

private  val LOG_TAG: String = "myLogs"
private val EXTRA_KEY: String = "key1"

class SecondActivity : AppCompatActivity() {
    var disBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        var extraValue = intent.getStringExtra(EXTRA_KEY)
//        Log.d(LOG_TAG, "SecondActivity onCreate $extraValue")
//        var textView = findViewById<TextView>(R.id.textViewMain)
//        textView.text = extraValue
        button_sec_test.setOnClickListener { HelpFragment().show(supportFragmentManager, "help") }
    }

    override fun onDestroy() {
        super.onDestroy()
        disBag.dispose()
        disBag.clear()
    }

    fun getDataSource(): Flowable<Int>
    {
        return Flowable.create ({ subscriber ->
            for(i in 0..100)
            {
                Thread.sleep(1000)
                subscriber.onNext(i)
            }
            subscriber.onComplete()
        }, BackpressureStrategy.DROP)
    }
}
