package de.mytoysgroup.movies.challenge.presentation.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.model.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.GetMovieByIdUseCase

class DetailPresenter : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val getMovieByIdUseCase by lazy { GetMovieByIdUseCase() }

    fun getMovie(movieId: String) = getMovieByIdUseCase.execute(movieId) {
        when (it) {
            is Either.Failure -> TODO()
            is Either.Success -> _movie.value = it.value
        }
    }
}