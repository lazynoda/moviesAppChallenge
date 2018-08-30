package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.omdb.OmdbRepository
import de.mytoysgroup.movies.challenge.domain.DataMapper
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie

class GetMovieByIdUseCase private constructor(private val omdbRepository: OmdbRepository) : UseCase<String, Movie>() {

    constructor() : this(OmdbRepository())

    override val inputMapper = object : DataMapper<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    override val outputMapper = object : DataMapper<Movie> {
        override fun fromMap(map: Map<String, Any?>) =
                Movie.fromMap(map)

        override fun toMap(value: Movie) =
                Movie.toMap(value)
    }

    override fun run(params: String) = omdbRepository.getMovieById(params)
}