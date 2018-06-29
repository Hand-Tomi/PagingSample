package com.sugaryple.pobcast.network

import retrofit2.adapter.rxjava2.Result

object ApiUtil {
    fun <T> successfulCheck(result: Result<T>) : T {
        return result.response()?.let { response ->
            if(response.isSuccessful) {
                return@let response.body()?.let {
                    it
                } ?: throw NullPointerException("body is null")
            } else {
                throw Exception("response not success ful ${response.errorBody()?.string()}" )
            }
        } ?: throw NullPointerException("response is null")
    }
}