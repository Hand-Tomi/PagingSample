package com.sugaryple.pobcast.util

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import com.sugaryple.pobcast.R

object Util {
    fun setOnPreDrawListener(view: View, listener: (View) -> Unit) {
        view.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                listener(view)
                return true
            }
        })
    }

    @JvmStatic
    fun convertMillisToTerm(context : Context, millis: Long) : String {
        val diff = System.currentTimeMillis() - millis
        val second = 1000L
        val minute = second * 60
        val hour = minute * 60
        val day = hour * 24
        val month = day * 30
        val year = month * 12
        return when {
            diff > year -> context.resources.getString(R.string.term_year, diff/year)
            diff > month -> context.resources.getString(R.string.term_month, diff/month)
            diff > day -> context.resources.getString(R.string.term_day, diff/day)
            diff > hour -> context.resources.getString(R.string.term_hour, diff/hour)
            diff > minute -> context.resources.getString(R.string.term_minute, diff/minute)
            else -> context.resources.getString(R.string.term_second, diff/second)
        }
    }
}