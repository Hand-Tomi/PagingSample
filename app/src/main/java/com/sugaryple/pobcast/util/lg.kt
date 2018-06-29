package com.sugaryple.pobcast.util

import android.util.Log

object lg {
    val TAG = "pobcast_log"

    fun v(vararg contents : Any?) {
        Log.v(TAG, contents.joinToString(" : "))
    }

    fun d(vararg contents : Any?) {
        Log.d(TAG, contents.joinToString(" : "))
    }

    fun e(vararg contents : Any?) {
        Log.e(TAG, contents.joinToString(" : "))
    }

    fun i(vararg contents : Any?) {
        Log.i(TAG, contents.joinToString(" : "))
    }
}