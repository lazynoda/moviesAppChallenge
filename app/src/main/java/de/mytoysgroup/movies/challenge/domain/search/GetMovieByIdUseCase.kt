package de.mytoysgroup.movies.challenge.domain.search

import android.content.Context
import de.mytoysgroup.movies.challenge.data.repository.omdb.OmdbRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.wishlist.IsMovieInWishlistUseCase

class GetMovieByIdUseCase private constructor(private val omdbRepository: OmdbRepository,
                                              isMovieInWishlistUseCase: IsMovieInWishlistUseCase?) : UseCase<String, Movie>() {

    constructor() : this(OmdbRepository(), null)
    constructor(context: Context) : this(OmdbRepository(), IsMovieInWishlistUseCase(context))

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.MOVIE

    private val isMovieInWishlistUseCase by lazy {
        isMovieInWishlistUseCase ?: IsMovieInWishlistUseCase(applicationContext)
    }

    override fun run(params: String) = omdbRepository.getMovieById(params).checkWishlist()

    private fun Movie.checkWishlist() = copy(wishlist = isMovieInWishlistUseCase.run(id))
}