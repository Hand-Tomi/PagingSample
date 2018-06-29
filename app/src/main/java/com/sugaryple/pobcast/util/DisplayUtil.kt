package com.sugaryple.pobcast.util

import android.content.Context

object DisplayUtil {

    var density: Float? = null
    fun getDensity(context: Context) : Float {
        val d = density ?: context.resources.displayMetrics.density
        if(density == null) density = d
        return d
    }
    fun getDpToPx(context: Context, dp: Int): Int {
        return (dp * getDensity(context)).toInt()
    }
}
