package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.omdb.OmdbRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.wishlist.IsMovieInWishlistUseCase

class SearchUseCase internal constructor(private val omdbRepository: OmdbRepository,
                                        isMovieInWishlistUseCase: IsMovieInWishlistUseCase?) : UseCase<String, List<Movie>>() {

    constructor() : this(OmdbRepository(), null)

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.MOVIE_LIST

    private val isMovieInWishlistUseCase by lazy {
        isMovieInWishlistUseCase ?: IsMovieInWishlistUseCase(applicationContext)
    }

    override fun run(params: String) = omdbRepository.search(params).checkWishlist()

    private fun List<Movie>.checkWishlist() = map { it.copy(wishlist = isMovieInWishlistUseCase.run(it.id)) }
}