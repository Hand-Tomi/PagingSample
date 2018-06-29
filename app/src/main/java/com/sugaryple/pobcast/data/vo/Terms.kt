package com.sugaryple.pobcast.data.vo

import com.google.gson.annotations.SerializedName


data class Terms(
    @SerializedName("terms")
    val terms: List<String>
)