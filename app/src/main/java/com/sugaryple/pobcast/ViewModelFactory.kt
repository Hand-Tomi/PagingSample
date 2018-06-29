package com.sugaryple.pobcast

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting

import com.sugaryple.pobcast.data.PodcastsRepository
import com.sugaryple.pobcast.ui.result.ResultViewModel
import com.sugaryple.pobcast.ui.home.HomeViewModel
import com.sugaryple.pobcast.ui.search.SearchViewModel

class ViewModelFactory private constructor(
        private val application: Application,
        private val podcastsRepository: PodcastsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(application, podcastsRepository)
                    isAssignableFrom(SearchViewModel::class.java) ->
                        SearchViewModel(application, podcastsRepository)
                    isAssignableFrom(ResultViewModel::class.java) ->
                        ResultViewModel(application, podcastsRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(application,
                            PodcastsRepository.getInstance(application.applicationContext))
                            .also { INSTANCE = it }
                }
    }
}
