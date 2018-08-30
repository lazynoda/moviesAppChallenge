package de.mytoysgroup.movies.challenge.data.repository.omdb

import android.net.Uri
import de.mytoysgroup.movies.challenge.data.network.NetworkClient
import de.mytoysgroup.movies.challenge.domain.model.Movie
import org.json.JSONArray
import org.json.JSONObject

internal class NetworkDataSource private constructor(private val networkClient: NetworkClient) {

    companion object {
        private const val HOST = "www.omdbapi.com"
        private const val API_KEY = "e67baad7"
    }

    constructor() : this(NetworkClient())

    fun search(query: String): List<Movie> {

        val uri = basicUri()
                .appendQueryParameter("s", query)
                .build()
        val jsonObject = networkClient.call(uri)
        return jsonObject.getJSONArray("Search").map {
            it.toMovie()
        }
    }

    fun getMovieById(movieId: String): Movie {

        val uri = basicUri()
                .appendQueryParameter("i", movieId)
                .build()
        val jsonObject = networkClient.call(uri)
        return jsonObject.toMovie()
    }

    private fun basicUri(): Uri.Builder {
        return Uri.Builder()
                .scheme("http")
                .authority(HOST)
                .appendQueryParameter("apikey", API_KEY)
    }

    private inline fun <R> JSONArray.map(transform: (JSONObject) -> R): List<R> =
            (0 until length()).map { transform(getJSONObject(it)) }

    private fun JSONObject.toMovie() = Movie(
            getString("imdbID"),
            getString("Title"),
            Uri.parse(getString("Poster"))
    )
}