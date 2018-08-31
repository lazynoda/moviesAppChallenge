package de.mytoysgroup.movies.challenge.presentation.detail

import android.os.Bundle
import de.mytoysgroup.movies.challenge.BaseActivity
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.load
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    private val presenter by lazy { presenter<DetailPresenter>(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        val pathData = intent?.data?.lastPathSegment ?: TODO()
        presenter.getMovie(pathData)

        setupObservers()
    }

    private fun setupObservers() = with(presenter) {
        movie.observe(::manageMovie)
    }

    private fun manageMovie(movie: Movie) {
        supportActionBar?.title = movie.title
        posterImage.load(movie.poster)
        titleLabel.text = movie.title
        descriptionLabel.text = movie.description
    }
}
