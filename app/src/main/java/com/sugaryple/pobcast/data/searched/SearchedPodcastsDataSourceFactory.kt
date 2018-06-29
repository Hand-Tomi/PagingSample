package com.sugaryple.pobcast.data.searched

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.sugaryple.pobcast.data.vo.Podcast
import com.sugaryple.pobcast.network.ListenNotesApi

class SearchedPodcastsDataSourceFactory(
        private val api: ListenNotesApi,
        private val query: String
) : DataSource.Factory<Int, Podcast>() {
    private val sourceLiveData = MutableLiveData<SearchedPodcastsDataSource>()
    override fun create(): DataSource<Int, Podcast> {
        val source = SearchedPodcastsDataSource(api, query)
        sourceLiveData.postValue(source)
        return source
    }
}