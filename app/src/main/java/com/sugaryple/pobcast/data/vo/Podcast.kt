package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class Podcast(
        @SerializedName("id")
        val id: String,
        @SerializedName("title_original")
        val titleOriginal: String,
        @SerializedName("description_original")
        val descriptionOriginal: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("publisher_highlighted")
        val publisherHighlighted: String
)