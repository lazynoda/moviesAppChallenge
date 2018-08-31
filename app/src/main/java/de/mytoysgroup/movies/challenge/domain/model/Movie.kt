package de.mytoysgroup.movies.challenge.domain.model

import android.net.Uri
import de.mytoysgroup.movies.challenge.domain.DataMapper

data class Movie(val id: String,
                 val title: String,
                 val wishlist: Boolean,
                 val poster: Uri,
                 val description: String? = null,
                 val website: Uri? = null,
                 val ratings: List<Rating>? = null) {

    companion object Mapper : DataMapper<Movie> {
        override fun fromMap(map: Map<String, Any?>) = Movie(
                map["id"] as String,
                map["title"] as String,
                map["wishlist"] as Boolean,
                Uri.parse(map["poster"] as String),
                map["description"] as? String?,
                (map["website"] as? String?)?.let(Uri::parse),
                (map["ratings"] as? List<Map<String, Any?>>?)?.map(Rating.Mapper::fromMap)
        )

        override fun toMap(value: Movie) = mapOf(
                "id" to value.id,
                "title" to value.title,
                "wishlist" to value.wishlist,
                "poster" to value.poster.toString(),
                "description" to value.description,
                "website" to value.website?.toString(),
                "ratings" to value.ratings?.map(Rating.Mapper::toMap)
        )
    }

    data class Rating(val source: String,
                      val value: String) {

        companion object Mapper : DataMapper<Rating> {
            override fun fromMap(map: Map<String, Any?>) = Rating(
                    map["source"] as String,
                    map["value"] as String
            )

            override fun toMap(value: Rating) = mapOf(
                    "source" to value.source,
                    "value" to value.value
            )
        }
    }
}