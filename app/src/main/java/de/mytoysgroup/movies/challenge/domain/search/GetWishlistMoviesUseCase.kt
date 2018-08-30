package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.DataMapper
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie

class GetWishlistMoviesUseCase private constructor(wishlistRepository: WishlistRepository?,
                                                   private val getMovieByIdUseCase: GetMovieByIdUseCase) : UseCase<Unit, List<Movie>>() {

    constructor() : this(null, GetMovieByIdUseCase())

    private val wishlistRepository by lazy {
        wishlistRepository ?: WishlistRepository(applicationContext)
    }

    override val inputMapper = object : DataMapper<Unit> {
        override fun fromMap(map: Map<String, Any?>) =
                Unit

        override fun toMap(value: Unit) =
                emptyMap<String, Any?>()
    }

    override val outputMapper = object : DataMapper<List<Movie>> {
        override fun fromMap(map: Map<String, Any?>) =
                (map["key"] as List<Map<String, Any?>>).map { Movie.fromMap(it) }

        override fun toMap(value: List<Movie>) =
                mapOf("key" to value.map { Movie.toMap(it) })
    }

    override fun run(params: Unit) = wishlistRepository.getAll()
            .map { getMovieByIdUseCase.run(it) }
}