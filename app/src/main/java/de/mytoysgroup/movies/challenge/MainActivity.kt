package de.mytoysgroup.movies.challenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val mainPresenter by lazy { ViewModelProviders.of(this).get(MainPresenter::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mainPresenter.addToWishlist().observe(this, Observer { _ ->
//            Log.d("TAG", "Movie added")
//        })

        mainPresenter.removeFromWishlist().observe(this, Observer { _ ->
            Log.d("TAG", "Movie deleted")
        })

        mainPresenter.getWishlist().observe(this, Observer { movies ->
            movies?.forEach {
                Log.d("TAG", "Movie $it")
            }
        })
    }
}
