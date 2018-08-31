package de.mytoysgroup.movies.challenge.presentation.detail

import android.os.Bundle
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.load
import de.mytoysgroup.movies.challenge.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    private val presenter by lazy { presenter<DetailPresenter>(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        setupObservers()

        val pathData = intent?.data?.lastPathSegment ?: TODO()
        presenter.getMovie(pathData)
    }

    private fun setupObservers() = with(presenter) {
        movie.observe(::manageMovie)
    }

    private fun manageMovie(movie: Movie) {
        supportActionBar?.title = movie.title
        posterImage.load(movie.poster)
        titleLabel.text = movie.title
        descriptionLabel.text = movie.description
        wishlistButton.setImageResource(if (movie.wishlist) R.drawable.ic_star else R.drawable.ic_star_border)
    }
}
