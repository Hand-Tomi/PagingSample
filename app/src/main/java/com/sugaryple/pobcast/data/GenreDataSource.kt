package com.sugaryple.pobcast.data

import com.sugaryple.pobcast.data.vo.Genre
import com.sugaryple.pobcast.data.vo.Genres
import com.sugaryple.pobcast.network.ApiUtil
import com.sugaryple.pobcast.network.ListenNotesApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GenreDataSource(
        private val api: ListenNotesApi
) {
    private val cacheNameToGenre: HashMap<String, Genre> = hashMapOf()
    private val cacheIdToGenre: HashMap<Int, Genre> = hashMapOf()

    fun convertNameToId(genreName: String, listener: (Genre?)->(Unit)) {
        if(cacheNameToGenre.isNotEmpty()) {
            listener.invoke(cacheNameToGenre[genreName])
            return
        }
        getRemoteGetGenre()
                .subscribe ({
                    listener.invoke(cacheNameToGenre[genreName])
                }, {
                    listener.invoke(null)
                    it.printStackTrace()
                })
    }

    private fun getRemoteGetGenre() : Observable<Genres>  {
        return api.service.getGenres()
                .subscribeOn(Schedulers.newThread())
                .map { ApiUtil.successfulCheck(it) }
                .map { genres ->
                    if(genres.genres.isNotEmpty()) {
                        synchronized(cacheNameToGenre) {
                            cacheNameToGenre.clear()
                            genres.genres.forEach { genre ->
                                cacheNameToGenre[genre.name] = genre
                            }
                        }
                        synchronized(cacheIdToGenre) {
                            cacheIdToGenre.clear()
                            genres.genres.forEach { genre ->
                                cacheIdToGenre[genre.id] = genre
                            }
                        }
                    }
                    genres
                }
                .observeOn(AndroidSchedulers.mainThread())
    }
}