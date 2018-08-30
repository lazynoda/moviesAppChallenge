package de.mytoysgroup.movies.challenge.presentation.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase
import de.mytoysgroup.movies.challenge.observeOnce

class SearchPresenter : ViewModel() {

    private val _searchData = MutableLiveData<List<Movie>>()
    val searchData: LiveData<List<Movie>> = _searchData

    private val searchUseCase by lazy { SearchUseCase() }

    fun search(query: String) {
        searchUseCase.execute(query).observeOnce {
            when (it) {
                is Either.Failure -> TODO("Show error")
                is Either.Success -> _searchData.value = it.value
            }
        }
    }

}
