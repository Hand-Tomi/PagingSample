package com.sugaryple.pobcast.data.searched

import android.arch.paging.PageKeyedDataSource
import com.sugaryple.pobcast.data.vo.Podcast
import com.sugaryple.pobcast.data.vo.SearchedPodcasts
import com.sugaryple.pobcast.network.ApiUtil
import com.sugaryple.pobcast.network.ListenNotesApi
import io.reactivex.Observable

/**
 * Google Architecture Paging
 * 検索したデータをロードする
 * @param query 検索ワード
 */
class SearchedPodcastsDataSource(
        private val api: ListenNotesApi,
        private val query: String
) : PageKeyedDataSource<Int, Podcast>() {

    //最初、一回
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Podcast>) {
        getRemoteSearchedPopcasts(query, FIRST_OFFSET)
                .subscribe({
                    //最後のページはnextOffsetとtotalの値は等しい
                    val nextPageNumber = if(it.nextOffset < it.total) { it.nextOffset } else { null }
                    callback.onResult(it.results, null, nextPageNumber)
                }, {
                    it.printStackTrace()
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Podcast>) {
        getRemoteSearchedPopcasts(query, params.key)
                .subscribe({
                    val nextPageNumber = if(it.nextOffset < it.total) { it.nextOffset } else { null }
                    callback.onResult(it.results, nextPageNumber)
                }, {
                    it.printStackTrace()
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Podcast>) {
        //無視されます
    }

    private fun getRemoteSearchedPopcasts(
            query: String,
            offset: Int
    ): Observable<SearchedPodcasts> {
        return api.service.getSearchedPopcasts(query, offset)
                .map { ApiUtil.successfulCheck(it) }
    }

    companion object {
        const val FIRST_OFFSET = 0
    }
}