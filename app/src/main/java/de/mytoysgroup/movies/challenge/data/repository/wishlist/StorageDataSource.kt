package de.mytoysgroup.movies.challenge.data.repository.wishlist

import android.content.Context
import android.content.SharedPreferences

internal class StorageDataSource(context: Context) {

    companion object {
        private const val WISHLIST_KEY = "wishlist_movies"
    }

    private val context = context.applicationContext

    private val sharedPreferences
        get() = context.getSharedPreferences("wishlist", Context.MODE_PRIVATE)

    fun add(movieId: String) = sharedPreferences.run {
        val stringSet = getStringSet(WISHLIST_KEY, mutableSetOf()) ?: mutableSetOf()
        stringSet.add(movieId)
        putStringSet(WISHLIST_KEY, stringSet)
    }

    fun remove(movieId: String) = sharedPreferences.run {
        val stringSet = getStringSet(WISHLIST_KEY, mutableSetOf()) ?: mutableSetOf()
        stringSet.remove(movieId)
        putStringSet(WISHLIST_KEY, stringSet)
    }

    fun getAll(): List<String> = sharedPreferences.getStringSet(WISHLIST_KEY, mutableSetOf())?.toList()
            ?: emptyList()

    private fun SharedPreferences.putStringSet(key: String, stringSet: MutableSet<String>) =
            edit { it.putStringSet(key, stringSet) }

    private fun SharedPreferences.edit(editAction: (SharedPreferences.Editor) -> Unit) =
            edit().also(editAction).apply()
}