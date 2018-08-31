package de.mytoysgroup.movies.challenge.domain.wishlist

import android.content.Context
import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase

class IsMovieInWishlistUseCase private constructor(wishlistRepository: WishlistRepository?) : UseCase<String, Boolean>() {

    constructor() : this(null)
    constructor(context: Context) : this(WishlistRepository(context))

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.BOOLEAN

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }

    override fun run(params: String): Boolean = wishlistRepository.getAll().contains(params)
}