package de.mytoysgroup.movies.challenge.presentation.list

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import de.mytoysgroup.movies.challenge.presentation.BaseActivity
import de.mytoysgroup.movies.challenge.presentation.Navigator.navigateTo
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : BaseActivity() {

    private val presenter by lazy { presenter<ListPresenter>(this) }

    private val adapter: SearchAdapter
        get() = searchResultLayout.adapter as SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar)

        with(searchResultLayout) {
            val span = if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation) 4 else 2
            layoutManager = GridLayoutManager(this@ListActivity, span, LinearLayoutManager.VERTICAL, false)
            adapter = SearchAdapter(::selectMovie)
        }

        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menu.setupSearchView()
        return true
    }

    private fun setupObservers() = with(presenter) {
        searchData.observe(::manageSearchResult)
    }

    private fun requestSearch(query: String) {
        presenter.search(query)
    }

    private fun manageSearchResult(movies: List<Movie>) {
        adapter.addNewItems(movies)
    }

    private fun selectMovie(movie: Movie) = navigateTo(movie.id)

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
}