package de.mytoysgroup.movies.challenge.presentation.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.Either
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.presenter
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    private val presenter by lazy { presenter<SearchPresenter>(this) }

    private val adapter: SearchAdapter
        get() = searchResultLayout.adapter as SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        with(searchResultLayout) {
            layoutManager = GridLayoutManager(this@SearchActivity, 2)
            adapter = SearchAdapter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menu.setupSearchView()
        return true
    }

    private fun requestSearch(query: String) {
        presenter.search(query).observe(::manageSearchResult)
    }

    private fun manageSearchResult(either: Either<Exception, List<Movie>>) {
        when (either) {
            is Either.Failure -> TODO("Show error")
            is Either.Success -> adapter.addNewItems(either.value)
        }
    }

    private fun Menu.setupSearchView() {
        val menuItem = findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                requestSearch(query)

                menuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return true
            }
        })
    }

    private inline fun <T> LiveData<T>.observe(crossinline function: (T) -> Unit) =
            observe(this@SearchActivity, Observer { it?.let(function) })
}