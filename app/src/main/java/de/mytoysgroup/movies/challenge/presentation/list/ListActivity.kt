package de.mytoysgroup.movies.challenge.presentation.list

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.presentation.BaseActivity
import de.mytoysgroup.movies.challenge.presentation.Navigator.navigateTo
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : BaseActivity() {

    private val presenter by lazy { presenter<ListPresenter>(this) }

    private val adapter: ListAdapter
        get() = searchResultLayout.adapter as ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar)

        with(searchResultLayout) {
            val span = if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation) 4 else 2
            layoutManager = GridLayoutManager(this@ListActivity, span, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter(::selectMovie, ::changeWishlist)
        }

        setupObservers()

        switchListButton.setOnClickListener(::onSwitchList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menu.setupSearchView()
        return true
    }

    private fun setupObservers() = with(presenter) {
        moviesLiveData.observe(::manageSearchResult)
    }

    private fun manageSearchResult(movies: List<Movie>) {
        adapter.updateItems(movies)
    }

    private fun requestSearch(query: String) {
        presenter.search(query)
    }

    private fun selectMovie(movie: Movie) = navigateTo(movie.id)

    private fun changeWishlist(movie: Movie) = presenter.changeWishlist(movie)

    private fun onSwitchList(view: View) {
        presenter.showWishlist()
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
}