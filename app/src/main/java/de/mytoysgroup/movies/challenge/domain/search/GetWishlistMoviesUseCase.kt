package de.mytoysgroup.movies.challenge.domain.search

import android.content.Context
import de.mytoysgroup.movies.challenge.data.repository.wishlist.WishlistRepository
import de.mytoysgroup.movies.challenge.domain.DataConverter
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie

class GetWishlistMoviesUseCase private constructor(private val wishlistRepository: WishlistRepository,
                                                   private val getMovieByIdUseCase: GetMovieByIdUseCase) : UseCase<String, List<Movie>>() {

    constructor(context: Context) : this(WishlistRepository(context), GetMovieByIdUseCase())

    override val inputConverter = object : DataConverter<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    override val outputConverter = object : DataConverter<List<Movie>> {
        override fun fromMap(map: Map<String, Any?>) =
                (map["key"] as List<Map<String, Any?>>).map { Movie.fromMap(it) }

        override fun toMap(value: List<Movie>) =
                mapOf("key" to value.map { Movie.toMap(it) })
    }

    override fun run(params: String) = wishlistRepository.getAll()
            .map { getMovieByIdUseCase.run(it) }
}