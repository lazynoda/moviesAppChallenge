package de.mytoysgroup.movies.challenge.domain.model

import android.net.Uri
import de.mytoysgroup.movies.challenge.domain.DataConverter

data class Movie(val id: String,
                 val title: String,
                 val poster: Uri) {

    companion object Converter : DataConverter<Movie> {
        override fun fromMap(map: Map<String, Any?>) = Movie(
                map["id"] as String,
                map["title"] as String,
                Uri.parse(map["poster"] as String)
        )

        override fun toMap(value: Movie) = mapOf(
                "id" to value.id,
                "title" to value.title,
                "poster" to value.poster.toString()
        )
    }
}