package de.mytoysgroup.movies.challenge

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase

class MainPresenter : ViewModel() {

    private val searchUseCase by lazy { SearchUseCase() }

    fun search(): LiveData<List<Movie>> = MutableLiveData<List<Movie>>().also {
        searchUseCase.execute("Avengers", it, MutableLiveData())
    }
}