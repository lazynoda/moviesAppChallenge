package de.mytoysgroup.movies.challenge.domain.search

import de.mytoysgroup.movies.challenge.data.repository.omdb.OmdbRepository
import de.mytoysgroup.movies.challenge.domain.Mappers
import de.mytoysgroup.movies.challenge.domain.UseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie

class GetMovieByIdUseCase private constructor(private val omdbRepository: OmdbRepository) : UseCase<String, Movie>() {

    constructor() : this(OmdbRepository())

    override val inputMapper = Mappers.STRING
    override val outputMapper = Mappers.MOVIE

    override fun run(params: String) = omdbRepository.getMovieById(params)
}