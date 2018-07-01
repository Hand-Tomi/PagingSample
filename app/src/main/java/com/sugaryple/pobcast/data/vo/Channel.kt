package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class Channel(
        @SerializedName("itunes_id")
        val itunesId: Long = 0L,
        @SerializedName("website")
        val website: String? = null,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("lastest_pub_date_ms")
        val lastestPubDateMs: Long = 0L,
        @SerializedName("description")
        val description: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("publisher")
        val publisher: String = "",
        @SerializedName("language")
        val language: String = "")