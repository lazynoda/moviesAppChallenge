package de.mytoysgroup.movies.challenge.data.repository.wishlist

import android.content.Context

class WishlistRepository internal constructor(private val storageDataSource: StorageDataSource) {

    constructor(context: Context) : this(StorageDataSource(context))

    fun add(movieId: String) =
            storageDataSource.add(movieId)

    fun remove(movieId: String) =
            storageDataSource.remove(movieId)

    fun getAll() =
            storageDataSource.getAll()
}