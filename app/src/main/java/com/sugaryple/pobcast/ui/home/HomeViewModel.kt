package com.sugaryple.pobcast.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.content.Context
import com.sugaryple.pobcast.data.PodcastsRepository
import com.sugaryple.pobcast.data.vo.Channel
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.support.annotation.MainThread
import android.widget.Toast
import com.sugaryple.pobcast.R


class HomeViewModel(
        context: Application,
        private val podcastsRepository: PodcastsRepository
) : AndroidViewModel(context) {
    private val genreId = MutableLiveData<Int?>()
    private val context: Context = context.applicationContext //メモリリークを避けるように
    val repoResult = switchMap(genreId) {
        podcastsRepository.postsBestPodcasts(it)
    }!!

    private fun showBestPodcasts(genreName: String) {
        setGenreName(genreName)
    }

    private fun setGenreName(genreName: String) {
        podcastsRepository.getGenreNameToId(genreName) {
            this.genreId.value = it?.id
        }
    }

    @MainThread
    fun goWebsite(channel: Channel) {
        if(channel.website != null) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(channel.website))
            startActivity(context, browserIntent, null)
        } else {
          Toast.makeText(context, R.string.toast_not_website, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onCreate
     */
    fun setUp() {
        showBestPodcasts("Podcasts")
    }

}
