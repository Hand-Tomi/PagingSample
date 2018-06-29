package com.sugaryple.pobcast.ui.search

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import com.sugaryple.pobcast.data.PodcastsRepository
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class SearchViewModel(
        context: Application,
        private val podcastsRepository: PodcastsRepository
) : AndroidViewModel(context) {
    val items: ObservableList<String> = ObservableArrayList()

    var disTypeaheadListTimer: Disposable? = null

    fun setNameTypeahead(str: String?) {
        //検索ワードが何かを書いたら0.5秒後にオススメ検索ワードリストを表示
        disTypeaheadListTimer?.dispose()
        if(str?.isEmpty() == true) {
            items.clear()
        } else {
            disTypeaheadListTimer = Observable.just(str)
                    .delay(MILLISECOND_TYPEAHEAD_LIST_TIMER, TimeUnit.MILLISECONDS)
                    .flatMap { podcastsRepository.getTypeaheadList(it) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        with(items) {
                            clear()
                            addAll(it)
                        }
                    }, {
                        items.clear()
                    })
        }
    }

    companion object {
        const val MILLISECOND_TYPEAHEAD_LIST_TIMER = 500L
    }
}
