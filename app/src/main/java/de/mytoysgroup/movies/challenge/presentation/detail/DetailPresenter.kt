package de.mytoysgroup.movies.challenge.presentation.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.model.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.GetMovieByIdUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.AddToWishlistUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.RemoveFromWishlistUseCase

class DetailPresenter(private val getMovieByIdUseCase: GetMovieByIdUseCase,
                      private val addToWishlistUseCase: AddToWishlistUseCase,
                      private val removeFromWishlistUseCase: RemoveFromWishlistUseCase) : ViewModel() {

    constructor() : this(GetMovieByIdUseCase(),
            AddToWishlistUseCase(),
            RemoveFromWishlistUseCase())

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    fun getMovie(movieId: String) = getMovieByIdUseCase.execute(movieId) {
        when (it) {
            is Either.Failure -> Unit // TODO: Show error
            is Either.Success -> _movie.value = it.value
        }
    }

    fun changeWishlist() {
        val movie = _movie.value ?: return
        if (movie.wishlist)
            removeFromWishlistUseCase.execute(movie.id) { wishlistUpdated(movie) }
        else
            addToWishlistUseCase.execute(movie.id) { wishlistUpdated(movie) }
    }

    private fun wishlistUpdated(movie: Movie) {
        _movie.value = movie.copy(wishlist = !movie.wishlist)
    }
}