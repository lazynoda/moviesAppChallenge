package de.mytoysgroup.movies.challenge.data.network

import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class NetworkClient {

    private val okHttpClient = OkHttpClient()

    fun call(uri: Uri): JSONObject {
        val request = Request.Builder()
                .url(uri.toString())
                .build()

        val response = okHttpClient.newCall(request).execute()
        return JSONObject(response.body()?.string())
    }
}