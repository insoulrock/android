package com.example.androidtestapp.ui.secondary

import com.badoo.binder.using
import com.badoo.binder.named
import com.badoo.mvicore.android.AndroidBindings
import com.example.androidtestapp.ui.secondary.viewmodel.UiEventViewToFeatureTransformer
import com.example.androidtestapp.ui.secondary.viewmodel.ViewModelTransformer

class SecondActivityBindings (view: SecondActivity,
                              private val feature: FeatureOne,
                              private val newsListener: NewsListener

):AndroidBindings<SecondActivity>(view){
    override fun setup(view: SecondActivity) {
        binder.bind(feature to view using ViewModelTransformer())
        binder.bind(view to feature using UiEventViewToFeatureTransformer())
        binder.bind(feature.news to newsListener)
    }
}