package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName

data class Genres(
        val genres: List<Genre>
)
data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("parent_id")
        val parentId: Int?,
        @SerializedName("name")
        val name: String
)