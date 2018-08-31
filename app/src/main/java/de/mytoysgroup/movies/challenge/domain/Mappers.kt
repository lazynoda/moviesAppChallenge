package de.mytoysgroup.movies.challenge.domain

import de.mytoysgroup.movies.challenge.domain.model.Movie

object Mappers {

    val STRING = object : DataMapper<String> {
        override fun fromMap(map: Map<String, Any?>) =
                map["key"] as String

        override fun toMap(value: String) =
                mapOf("key" to value)
    }

    val UNIT = object : DataMapper<Unit> {
        override fun fromMap(map: Map<String, Any?>) =
                Unit

        override fun toMap(value: Unit) =
                emptyMap<String, Any?>()
    }

    val MOVIE = object : DataMapper<Movie> {
        override fun fromMap(map: Map<String, Any?>) =
                Movie.fromMap(map)

        override fun toMap(value: Movie) =
                Movie.toMap(value)
    }

    val MOVIE_LIST = object : DataMapper<List<Movie>> {
        @Suppress("UNCHECKED_CAST")
        override fun fromMap(map: Map<String, Any?>) =
                (map["key"] as List<Map<String, Any?>>).map(Movie.Mapper::fromMap)

        override fun toMap(value: List<Movie>) =
                mapOf("key" to value.map(Movie.Mapper::toMap))
    }
}