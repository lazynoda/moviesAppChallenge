package de.mytoysgroup.movies.challenge

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

inline fun <reified T : ViewModel> presenter(activity: FragmentActivity): T =
        ViewModelProviders.of(activity).get<T>(T::class.java)
