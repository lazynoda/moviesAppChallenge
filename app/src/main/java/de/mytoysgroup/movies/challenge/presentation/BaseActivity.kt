package de.mytoysgroup.movies.challenge.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected inline fun <T> LiveData<T>.observe(crossinline function: (T) -> Unit) =
            observe(this@BaseActivity, Observer { it?.let(function) })

    protected inline fun <reified T : ViewModel> presenter(activity: FragmentActivity): T =
            ViewModelProviders.of(activity).get<T>(T::class.java)
}
