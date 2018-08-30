package de.mytoysgroup.movies.challenge.data.repository.omdb

class OmdbRepository private constructor(private val networkDataSource: NetworkDataSource) {

    constructor() : this(NetworkDataSource())

    fun search(query: String) =
            networkDataSource.search(query)
}