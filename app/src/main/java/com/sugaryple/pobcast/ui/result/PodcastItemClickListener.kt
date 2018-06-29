package com.sugaryple.pobcast.ui.result

import com.sugaryple.pobcast.data.vo.Podcast

interface PodcastItemClickListener {
    fun onClick(podcast: Podcast)
}