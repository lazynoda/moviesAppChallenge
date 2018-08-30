package de.mytoysgroup.movies.challenge.presentation.search

import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase

class SearchPresenter : ViewModel() {

    private val searchUseCase by lazy { SearchUseCase() }

    fun search(query: String) = searchUseCase.execute(query)

}
