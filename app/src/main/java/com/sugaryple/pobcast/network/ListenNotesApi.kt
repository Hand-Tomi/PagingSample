package com.sugaryple.pobcast.network

import android.content.Context
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.data.vo.*
import io.reactivex.Observable
import okhttp3.Headers
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ListenNotesApi(
        context: Context
): BaseApi<ListenNotesApi.ServiceList>(
        context,
        "https://listennotes.p.mashape.com/",
        ServiceList::class.java
) {

    private val key = context.getString(R.string.key_listen_notes_api)

    override fun getBaseHeaders(): Headers {
        return Headers.Builder()
                .add("X-Mashape-Key", key)
                .add("Accept", "application/json")
                .build()
    }

    interface ServiceList {
        @GET("/api/v1/best_podcasts")
        fun getBestPopcasts(@Query("page") page: Int,
                            @Query("genre_id") genreId: Int? = null)
                : Observable<Result<BestPodcasts>>

        @GET("/api/v1/genres")
        fun getGenres(): Observable<Result<Genres>>

        @GET("/api/v1/typeahead")
        fun getTypeaheads(@Query("q") q: String)
                : Observable<Result<Terms>>

        @GET("/api/v1/search")
        fun getSearchedPopcasts(@Query("q") page: String,
                                @Query("offset") offset: Int,
                                @Query("type") genreId: String = "podcast")
                : Observable<Result<SearchedPodcasts>>

        @GET("/api/v1/podcasts/{id}")
        fun getPodcastInfo(@Path("id") id: String): Observable<Result<PodcastInfo>>
    }

    companion object {
        private var INSTANCE: ListenNotesApi? = null

        private val lock = Any()

        fun getInstance(context: Context): ListenNotesApi {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = ListenNotesApi(context.applicationContext)
                }
                return INSTANCE!!
            }
        }
    }

}