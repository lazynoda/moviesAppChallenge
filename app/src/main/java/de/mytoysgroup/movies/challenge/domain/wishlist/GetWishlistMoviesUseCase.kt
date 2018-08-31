package de.mytoysgroup.movies.challenge.domain.wishlist

import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.GetMovieByIdUseCase

class GetWishlistMoviesUseCase private constructor(wishlistRepository: WishlistRepository?,
                                                   getMovieByIdUseCase: GetMovieByIdUseCase?) : UseCase<Unit, List<Movie>>() {

    constructor() : this(null, null)

    override val inputMapper = Mappers.UNIT
    override val outputMapper = Mappers.MOVIE_LIST

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }
    private val getMovieByIdUseCase by lazy {
        getMovieByIdUseCase ?: GetMovieByIdUseCase(applicationContext)
    }

    override fun run(params: Unit) = wishlistRepository.getAll()
            .map { getMovieByIdUseCase.run(it) }
}