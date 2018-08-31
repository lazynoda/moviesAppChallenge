package de.mytoysgroup.movies.challenge.presentation.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.model.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.AddToWishlistUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.GetWishlistMoviesUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.RemoveFromWishlistUseCase
import kotlin.properties.Delegates

class ListPresenter(private val searchUseCase: SearchUseCase,
                    private val getWishlistMoviesUseCase: GetWishlistMoviesUseCase,
                    private val addToWishlistUseCase: AddToWishlistUseCase,
                    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase) : ViewModel() {

    constructor() : this(SearchUseCase(),
            GetWishlistMoviesUseCase(),
            AddToWishlistUseCase(),
            RemoveFromWishlistUseCase())

    val moviesLiveData: LiveData<List<Movie>>
        get() = _moviesLiveData

    private val _moviesLiveData = MutableLiveData<List<Movie>>()

    private var viewType by Delegates.observable<ViewType>(ViewType.Wishlist) { _, oldValue, newValue ->
        if (oldValue != newValue)
            updateView(newValue)
    }
    private var query: String? = null

    init {
        updateView(viewType)
    }

    fun search(query: String) {
        this.query = query
        viewType = ViewType.Search
    }

    fun showWishlist() {
        viewType = ViewType.Wishlist
    }

    fun changeWishlist(movie: Movie) =
            if (movie.wishlist)
                removeFromWishlistUseCase.execute(movie.id) { wishlistUpdated(movie) }
            else
                addToWishlistUseCase.execute(movie.id) { wishlistUpdated(movie) }

    private fun performSearch(query: String?) {
        query ?: return
        searchUseCase.execute(query) {
            when (it) {
                is Either.Failure -> Unit // TODO: Show error
                is Either.Success -> {
                    _moviesLiveData.value = it.value
                }
            }
        }
    }

    private fun updateView(viewType: ViewType) {
        when (viewType) {
            is ViewType.Search -> performSearch(query)
            is ViewType.Wishlist -> retrieveWishlist()
        }
    }

    private fun retrieveWishlist() = getWishlistMoviesUseCase.execute(Unit) {
        when (it) {
            is Either.Failure -> Unit // TODO: Show error
            is Either.Success -> {
                _moviesLiveData.value = it.value
            }
        }
    }

    private fun wishlistUpdated(movie: Movie) {
        val newMoviesList = if (movie.wishlist && ViewType.Wishlist == viewType)
            _moviesLiveData.value?.filter { movie.id != it.id }
        else
            _moviesLiveData.value?.map { if (movie.id == it.id) it.copy(wishlist = !it.wishlist) else it }

        _moviesLiveData.value = newMoviesList ?: emptyList()
    }

    sealed class ViewType {
        object Search : ViewType()
        object Wishlist : ViewType()
    }
}
