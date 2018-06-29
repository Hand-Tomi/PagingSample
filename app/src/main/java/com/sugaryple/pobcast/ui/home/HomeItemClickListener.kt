package com.sugaryple.pobcast.ui.home

import com.sugaryple.pobcast.data.vo.Channel

interface HomeItemClickListener {
    fun onClick(channel: Channel)
}