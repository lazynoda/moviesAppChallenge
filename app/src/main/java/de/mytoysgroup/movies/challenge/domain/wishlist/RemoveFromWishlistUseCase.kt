package de.mytoysgroup.movies.challenge.domain.wishlist

import android.content.Context
import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase

class RemoveFromWishlistUseCase internal constructor(wishlistRepository: WishlistRepository? = null) : UseCase<String, Unit>() {

    constructor() : this(null)
    constructor(context: Context) : this(WishlistRepository(context))

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.UNIT

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }

    override fun run(params: String) {
        val updated = wishlistRepository.remove(params)
        if (!updated) throw RuntimeException()
    }
}