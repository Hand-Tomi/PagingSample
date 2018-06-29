package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

/**
 * 今には必要なデータはwebsiteのみ
 */
data class PodcastInfo(
        @SerializedName("website")
        val website: String
)