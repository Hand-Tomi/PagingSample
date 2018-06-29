package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class Channel(
        @SerializedName("itunes_id")
        val itunesId: Long,
        @SerializedName("website")
        val website: String?,
        @SerializedName("title")
        val title: String,
        @SerializedName("lastest_pub_date_ms")
        val lastestPubDateMs: Long,
        @SerializedName("description")
        val description: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("language")
        val language: String) {
}