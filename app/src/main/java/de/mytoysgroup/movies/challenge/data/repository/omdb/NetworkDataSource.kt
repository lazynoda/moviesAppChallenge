package de.mytoysgroup.movies.challenge.data.repository.omdb

import android.net.Uri
import de.mytoysgroup.movies.challenge.data.network.NetworkClient
import de.mytoysgroup.movies.challenge.domain.model.Movie
import org.json.JSONArray
import org.json.JSONObject

internal class NetworkDataSource private constructor(private val networkClient: NetworkClient) {

    constructor() : this(NetworkClient())

    fun search(query: String): List<Movie> {

        val uri = Uri.Builder()
                .scheme("http")
                .authority("www.omdbapi.com")
                .appendQueryParameter("apikey", "e67baad7")
                .appendQueryParameter("s", query)
                .build()
        val jsonObject = networkClient.call(uri)

        return jsonObject.getJSONArray("Search").map {
            Movie(it.getString("imdbID"),
                    it.getString("Title"),
                    Uri.parse(it.getString("Poster")))
        }
    }

    private inline fun <R> JSONArray.map(transform: (JSONObject) -> R): List<R> =
            (0 until length()).map { transform(getJSONObject(it)) }
}