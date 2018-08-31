package de.mytoysgroup.movies.challenge.data.repository.wishlist

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("ApplySharedPref")
internal class StorageDataSource(context: Context) {

    companion object {
        private const val WISHLIST_KEY = "wishlist_movies"
    }

    private val context = context.applicationContext

    private val sharedPreferences
        get() = context.getSharedPreferences("wishlist", Context.MODE_PRIVATE)

    fun add(movieId: String): Boolean {
        val wishlist = getAll().toSet() + movieId
        return sharedPreferences.edit()
                .putStringSet(WISHLIST_KEY, wishlist)
                .commit()
    }

    fun remove(movieId: String): Boolean {
        val wishlist = getAll().toSet() - movieId
        return sharedPreferences.edit()
                .putStringSet(WISHLIST_KEY, wishlist)
                .commit()
    }

    fun getAll(): List<String> = sharedPreferences.getStringSet(WISHLIST_KEY, mutableSetOf())?.toList()
            ?: emptyList()
}