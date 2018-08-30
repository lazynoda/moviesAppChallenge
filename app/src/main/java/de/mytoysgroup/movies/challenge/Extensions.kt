package de.mytoysgroup.movies.challenge

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.load(uri: Uri) {
    Glide.with(this)
            .load(uri)
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                    if (resource != null) {
                        setImageDrawable(resource)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    } else {
                        setImageResource(R.drawable.ic_broken_image)
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                    }
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    setImageResource(R.drawable.ic_photo)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    setImageResource(R.drawable.ic_broken_image)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }
            })
}

inline fun <T> LiveData<T>.observeOnce(crossinline action: (workStatus: T?) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T?) {
            removeObserver(this)
            action(value)
        }
    })
}