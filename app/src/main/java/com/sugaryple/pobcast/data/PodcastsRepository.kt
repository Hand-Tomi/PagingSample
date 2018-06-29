package com.sugaryple.pobcast.data

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.sugaryple.pobcast.data.best.BestPodcastsDataSourceFactory
import com.sugaryple.pobcast.data.searched.SearchedPodcastsDataSourceFactory
import com.sugaryple.pobcast.data.vo.Channel
import com.sugaryple.pobcast.data.vo.Genre
import com.sugaryple.pobcast.data.vo.Podcast
import com.sugaryple.pobcast.network.ApiUtil
import com.sugaryple.pobcast.network.ListenNotesApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class PodcastsRepository(context: Context) {

    private val api = ListenNotesApi.getInstance(context)
    private val genreDataSource = GenreDataSource(api)

    fun postsBestPodcasts(genreId: Int?) : LiveData<PagedList<Channel>> {
        val sourceFactory = BestPodcastsDataSourceFactory(api, genreId)
        return LivePagedListBuilder(sourceFactory, BEST_PODCASTS_PAGE_COUNT)
                .setFetchExecutor(NETWORK_EXECUTORS)
                .build()
    }

    fun postsSearchedPodcasts(query: String) : LiveData<PagedList<Podcast>> {
        val sourceFactory = SearchedPodcastsDataSourceFactory(api, query)
        return LivePagedListBuilder(sourceFactory, SEARCHED_PODCASTS_PAGE_COUNT)
                .setFetchExecutor(NETWORK_EXECUTORS)
                .build()
    }

    fun getGenreNameToId(genreName: String, listener: (Genre?)->(Unit)) {
        genreDataSource.convertNameToId(genreName, listener)
    }

    fun getTypeaheadList(str: String) : Observable<List<String>> {
        return api.service.getTypeaheads(str)
                .subscribeOn(Schedulers.io())
                .map { ApiUtil.successfulCheck(it) }
                .map { it.terms }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWebsite(id: String): Observable<String> {
        //検索したデータの中にはwebsiteがなくて詳細情報をロードする
        return api.service.getPodcastInfo(id)
                .subscribeOn(Schedulers.io())
                .map { ApiUtil.successfulCheck(it) }
                .map { it.website }
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        const val BEST_PODCASTS_PAGE_COUNT = 20
        const val SEARCHED_PODCASTS_PAGE_COUNT = 20
        private const val NETWORK_EXECUTORS_THREAD_COUNT = 5
        val NETWORK_EXECUTORS = Executors.newFixedThreadPool(NETWORK_EXECUTORS_THREAD_COUNT)!!

        private var INSTANCE: PodcastsRepository? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(PodcastsRepository::class.java) {
                    INSTANCE ?: PodcastsRepository(context)
                            .also { INSTANCE = it }
                }
    }
}