package com.sugaryple.pobcast.ui.result

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.content.Intent
import android.databinding.ObservableField
import android.net.Uri
import android.support.annotation.MainThread
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.data.PodcastsRepository
import com.sugaryple.pobcast.data.vo.Podcast

class ResultViewModel(
        context: Application,
        private val podcastsRepository: PodcastsRepository
) : AndroidViewModel(context) {
    private val query = MutableLiveData<String?>()
    private val context: Context = context.applicationContext //メモリリークを避けるように
    val repoResult = Transformations.switchMap(query) { q ->
        q?.let { podcastsRepository.postsSearchedPodcasts(it) }
    }!!
    val search: ObservableField<String> = ObservableField()

    /**
     * 検索結果表示
     */
    fun showSearchedPodcasts(query: String) {
        this.query.value = query
        search.set(query)
    }

    /**
     * ウェブサイト移動
     */
    @MainThread
    fun goWebsite(podcast: Podcast) {
        podcastsRepository.getWebsite(podcast.id)
                .subscribe ({
                    if(it != null) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        ContextCompat.startActivity(context, browserIntent, null)
                    } else {
                        Toast.makeText(context, R.string.toast_not_website, Toast.LENGTH_SHORT).show()
                    }
                }, {
                    it.printStackTrace()
                    Toast.makeText(context, R.string.toast_not_website, Toast.LENGTH_SHORT).show()
                })
    }
}