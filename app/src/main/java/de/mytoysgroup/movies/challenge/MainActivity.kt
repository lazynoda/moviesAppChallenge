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

        mainPresenter.search().observe(this, Observer { movies ->
            movies ?: return@Observer
            Log.d("TAG", "Salida ${movies.size}")
            movies.forEach {
                Log.d("TAG", "Salida $it")
            }
        })
    }
}
