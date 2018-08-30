package de.mytoysgroup.movies.challenge

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(uri: Uri) {
    Glide.with(this)
            .load(uri)
            .into(this)
}
