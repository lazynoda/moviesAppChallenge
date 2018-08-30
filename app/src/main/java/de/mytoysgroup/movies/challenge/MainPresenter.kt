package de.mytoysgroup.movies.challenge

import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.search.*

class MainPresenter : ViewModel() {

    private val searchUseCase by lazy { SearchUseCase() }
    private val getMovieByIdUseCase by lazy { GetMovieByIdUseCase() }
    private val addToWishlistUseCase by lazy { AddToWishlistUseCase() }
    private val removeFromWishlistUseCase by lazy { RemoveFromWishlistUseCase() }
    private val getWishlistUseCase by lazy { GetWishlistMoviesUseCase() }

    fun search() =
            searchUseCase.execute("Avengers")

    fun getMovieById() =
            getMovieByIdUseCase.execute("tt0803093")

    fun addToWishlist() =
            addToWishlistUseCase.execute("tt0803093")

    fun removeFromWishlist() =
            removeFromWishlistUseCase.execute("tt0803093")

    fun getWishlist() =
            getWishlistUseCase.execute(Unit)
}
