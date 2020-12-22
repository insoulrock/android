package com.example.androidtestapp.ui.recyclerview

import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.example.androidtestapp.ui.recyclerview.viewmodel.UiEventViewToFeatureTransformer
import com.example.androidtestapp.ui.recyclerview.viewmodel.ViewModelTransformer

class RecyclerViewBindings (view: FragmentRecyclerView,
                              private val feature: FeatureRecyclerView,
                              private val newsListener: NewsListener

): AndroidBindings<FragmentRecyclerView>(view){
    override fun setup(view: FragmentRecyclerView) {
        binder.bind(feature to view using ViewModelTransformer())
        binder.bind(view to feature using UiEventViewToFeatureTransformer())
        binder.bind(feature.news to newsListener)
    }
}