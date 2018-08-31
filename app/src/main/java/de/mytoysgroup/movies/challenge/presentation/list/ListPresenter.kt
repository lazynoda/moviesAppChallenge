package de.mytoysgroup.movies.challenge.presentation.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.model.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.GetWishlistMoviesUseCase
import kotlin.properties.Delegates

class ListPresenter : ViewModel() {

    val moviesLiveData: LiveData<List<Movie>>
        get() = _moviesLiveData

    private val _moviesLiveData = MutableLiveData<List<Movie>>()

    private val searchUseCase by lazy { SearchUseCase() }
    private val getWishlistMoviesUseCase by lazy { GetWishlistMoviesUseCase() }

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

    sealed class ViewType {
        object Search : ViewType()
        object Wishlist : ViewType()
    }
}
