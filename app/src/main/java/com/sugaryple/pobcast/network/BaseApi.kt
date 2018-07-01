package com.sugaryple.pobcast.network

import android.content.Context
import com.sugaryple.pobcast.R
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseApi<S>(context: Context, basePath: String, serviceClass: Class<S>) {

    val service: S
    private val context: Context = context.applicationContext //メモリリークを避けるように
    private val logging by lazy { HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } }

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(basePath)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getClient())
            .build()

    init {
        service = retrofit.create(serviceClass)
    }

    private fun getClient(): OkHttpClient {
         val builder = OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()
                    val request = original.newBuilder()
                            .headers(getBaseHeaders())
                            .method(original.method(), original.body())
                            .build()
                    return@addInterceptor it.proceed(request)
                }
//        if(context.resources.getBoolean(R.bool.debug)) {
            //TODO Debug--
            builder.addInterceptor(logging)
//        }
        return builder.build()
    }

    abstract fun getBaseHeaders(): Headers



}