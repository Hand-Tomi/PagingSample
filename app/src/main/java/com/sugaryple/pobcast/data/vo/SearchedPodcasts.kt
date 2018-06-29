package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class SearchedPodcasts(
        @SerializedName("total")
        val total: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("next_offset")
        val nextOffset: Int,
        @SerializedName("results")
        val results: List<Podcast>
)