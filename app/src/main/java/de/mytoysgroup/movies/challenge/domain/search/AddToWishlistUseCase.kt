package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.DataMapper
import de.mytoysgroup.movies.challenge.domain.UseCase

class AddToWishlistUseCase private constructor(wishlistRepository: WishlistRepository?) : UseCase<String, Unit>() {

    constructor() : this(null)

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }

    override val inputMapper = object : DataMapper<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    override val outputMapper = object : DataMapper<Unit> {
        override fun fromMap(map: Map<String, Any?>) =
                Unit

        override fun toMap(value: Unit) =
                emptyMap<String, Any?>()
    }

    override fun run(params: String) = wishlistRepository.add(params)
}