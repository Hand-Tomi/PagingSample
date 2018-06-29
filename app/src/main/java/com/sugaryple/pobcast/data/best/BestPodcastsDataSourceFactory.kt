package com.sugaryple.pobcast.data.best

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.sugaryple.pobcast.data.vo.Channel
import com.sugaryple.pobcast.network.ListenNotesApi

class BestPodcastsDataSourceFactory(
        private val api: ListenNotesApi,
        private val genreId: Int?
) : DataSource.Factory<Int, Channel>() {
    private val sourceLiveData = MutableLiveData<BestPodcastsDataSource>()
    override fun create(): DataSource<Int, Channel> {
        val source = BestPodcastsDataSource(api, genreId)
        sourceLiveData.postValue(source)
        return source
    }
}