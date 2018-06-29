package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class BestPodcasts(
        @SerializedName("id")
        val id: Int,

        @SerializedName("page_number")
        val pageNumber: Int,

        @SerializedName("has_next")
        val hasNext: Boolean,

        @SerializedName("next_page_number")
        val nextPageNumber: Int,

        @SerializedName("has_previous")
        val hasPrevious: Boolean,

        @SerializedName("previous_page_number")
        val previousPageNumber: Int,

        @SerializedName("channels")
        val channels: List<Channel>
)