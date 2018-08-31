package de.mytoysgroup.movies.challenge

import android.content.Context
import android.content.Intent
import android.net.Uri

object Navigator {

    fun Context.navigateTo(route: String) {
        val uri = Uri.Builder()
                .scheme(getString(R.string.deep_linking_scheme))
                .authority(getString(R.string.deep_linking_host))
                .path(route)
                .build()

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            `package` = packageName
        }
        startActivity(intent)
    }
}