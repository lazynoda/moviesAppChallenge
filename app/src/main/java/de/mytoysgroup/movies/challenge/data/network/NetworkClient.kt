package de.mytoysgroup.movies.challenge.data.network

import android.net.Uri
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class NetworkClient {

    companion object {
        private val LOG_TAG = NetworkClient::class.java.simpleName
    }

    private val okHttpClient = OkHttpClient()

    fun call(uri: Uri): JSONObject {
        val request = Request.Builder()
                .url(uri.toString())
                .build()

        Log.d(LOG_TAG, "Network request:\n$request")
        val response = okHttpClient.newCall(request).execute()
        val jsonResponse = JSONObject(response.body()?.string())
        Log.d(LOG_TAG, "Network response:\n$response\n${jsonResponse.toString(2)}")
        return jsonResponse
    }
}