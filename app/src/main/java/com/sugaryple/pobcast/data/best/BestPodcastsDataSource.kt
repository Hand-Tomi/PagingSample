package com.sugaryple.pobcast.data.best

import android.arch.paging.PageKeyedDataSource
import com.sugaryple.pobcast.data.vo.Channel
import com.sugaryple.pobcast.network.ApiUtil
import com.sugaryple.pobcast.network.ListenNotesApi

/**
 * Google Architecture Paging
 * ベストPodcastリストロード
 * @param query ジャンル
 */
class BestPodcastsDataSource(
        private val api: ListenNotesApi,
        private val genreId: Int?
) : PageKeyedDataSource<Int, Channel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Channel>) {
        api.service.getBestPopcasts(FIRST_PAGE, genreId)
                .map { ApiUtil.successfulCheck(it) }
                .subscribe({
                    val nextPageNumber = if(it.hasNext) { it.nextPageNumber } else { null }
                    val previousPageNumber = if(it.hasPrevious) { it.previousPageNumber } else { null }
                    callback.onResult(it.channels, previousPageNumber, nextPageNumber)
                }, {
                    it.printStackTrace()
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Channel>) {
        api.service.getBestPopcasts(params.key, genreId)
                .map { ApiUtil.successfulCheck(it) }
                .subscribe({
                    val nextPageNumber = if(it.hasNext) { it.nextPageNumber } else { null }
                    callback.onResult(it.channels, nextPageNumber)
                }, {
                    it.printStackTrace()
                })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Channel>) {
        api.service.getBestPopcasts(params.key, genreId)
                .map { ApiUtil.successfulCheck(it) }
                .subscribe({
                    val previousPageNumber = if(it.hasPrevious) { it.previousPageNumber } else { null }
                    callback.onResult(it.channels, previousPageNumber)
                }, {
                    it.printStackTrace()
                })
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}