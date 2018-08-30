package de.mytoysgroup.movies.challenge.domain.search

import android.content.Context
import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.DataConverter
import de.mytoysgroup.movies.challenge.domain.UseCase

class RemoveFromWishlistUseCase private constructor(private val wishlistRepository: WishlistRepository) : UseCase<String, Unit>() {

    constructor(context: Context) : this(WishlistRepository(context))

    override val inputConverter = object : DataConverter<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    override val outputConverter = object : DataConverter<Unit> {
        override fun fromMap(map: Map<String, Any?>) =
                Unit

        override fun toMap(value: Unit) =
                emptyMap<String, Any?>()
    }

    override fun run(params: String) = wishlistRepository.remove(params)
}