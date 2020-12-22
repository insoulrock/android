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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class FragmentRecyclerView : ObservableSourceFragment<UiEvent>(), Consumer<ViewModel> {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var disBag: CompositeDisposable = CompositeDisposable()
    private val LOG_TAG = FragmentRecyclerView::class.java.simpleName
//    private val subscriber: RecyclerViewHelper by inject()
    private val bindings: RecyclerViewBindings = RecyclerViewBindings(this, FeatureRecyclerView(), NewsListener(context))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun accept(t: ViewModel) {
//        textEditFilter.setText(t.filter)
//        switchSortDesc.isChecked = t.needSort
    }

    private fun setupViews(){
        textEditFilter.setOnClickListener { onNext(UiEvent.FilterEntered("R")) }
        switchSortDesc.setOnClickListener { onNext(UiEvent.SortingSwitched(enabled = false)) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindings.setup(this)
//        disBag.add(RxTextView.textChanges(textEditFilter)
//            .debounce(300, TimeUnit.MILLISECONDS)
//            .map {
//                Log.d("RecyclerViewHelper", "filter = $it.toString() ")
//                it.toString()
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                onNext(UiEvent.FilterEntered("RRR"))
//            },
//                {
//                    Log.e(LOG_TAG, it.localizedMessage)
//                }
//            )
//        )

//       disBag.add(RxView.clicks(switchSortDesc)
//            .map {
//                Log.d("RecyclerViewHelper", "percentSort = $switchSortDesc.isChecked")
//                switchSortDesc.isChecked
//            }
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                onNext(UiEvent.SortingSwitched(switchSortDesc.isChecked))
//            },
//                {
//                    Log.e(LOG_TAG, it.localizedMessage)
//                }
//            ))

//        subscriber.startListen(ss0, ss)
//        recyclerAdapter = RecyclerAdapter(view.context)
//        initRecyclerView()
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