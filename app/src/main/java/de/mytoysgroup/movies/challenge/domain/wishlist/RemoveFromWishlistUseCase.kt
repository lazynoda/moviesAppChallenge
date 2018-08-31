package de.mytoysgroup.movies.challenge.domain.wishlist

import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase

class RemoveFromWishlistUseCase private constructor(wishlistRepository: WishlistRepository? = null) : UseCase<String, Unit>() {

    constructor() : this(null)

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.UNIT

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }

    override fun run(params: String) = wishlistRepository.remove(params)
}