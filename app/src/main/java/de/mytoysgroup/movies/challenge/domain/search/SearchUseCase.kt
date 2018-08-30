package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.omdb.OmdbRepository
import de.mytoysgroup.movies.challenge.domain.DataMapper
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie

class SearchUseCase private constructor(private val omdbRepository: OmdbRepository) : UseCase<String, List<Movie>>() {

    constructor() : this(OmdbRepository())

    override val inputMapper = object : DataMapper<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    override val outputMapper = object : DataMapper<List<Movie>> {
        override fun fromMap(map: Map<String, Any?>) =
                (map["key"] as List<Map<String, Any?>>).map { Movie.fromMap(it) }

        override fun toMap(value: List<Movie>) =
                mapOf("key" to value.map { Movie.toMap(it) })
    }

    override fun run(params: String) = omdbRepository.search(params)
}