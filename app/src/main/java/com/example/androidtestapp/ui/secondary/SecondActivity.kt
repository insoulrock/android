package com.example.androidtestapp.ui.secondary

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.androidtestapp.R
import com.example.androidtestapp.ui.common.ObservableSourceActivity
import com.example.androidtestapp.ui.secondary.events.UiEvent
import com.example.androidtestapp.ui.secondary.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_second.*

private  val LOG_TAG: String = "myLogs"
private val EXTRA_KEY: String = "key1"

class SecondActivity : ObservableSourceActivity<UiEvent>(), Consumer<ViewModel> {
    var disBag = CompositeDisposable()
    var bindings: SecondActivityBindings = SecondActivityBindings(this,
        FeatureOne(), NewsListener(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setupViews()
        bindings.setup(this)
    }
    private fun setupViews(){
        buttonLeft.setOnClickListener { onNext(UiEvent.LeftButtonClicked) }
        buttonCenter.setOnClickListener { onNext(UiEvent.CenterButtonClicked) }
        buttonRight.setOnClickListener { onNext(UiEvent.RightButtonClicked) }
        buttonReset.setOnClickListener { onNext(UiEvent.ResetButtonClicked) }
    }

    override fun accept(viewModel: ViewModel) {
        textEditCounter.setText(viewModel.counter.toString(), TextView.BufferType.EDITABLE)
        buttonReset.visibility = if(viewModel.resetIsAvailable) View.VISIBLE else View.GONE
        textViewLastReset.setText(viewModel.lastDtReset.toLocaleString(), TextView.BufferType.NORMAL)
        textViewLastIncrease.setText(viewModel.lastDtIncrease.toGMTString(), TextView.BufferType.NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        disBag.dispose()
        disBag.clear()
    }
}
