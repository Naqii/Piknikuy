package com.example.piknikuy.api

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestHandle

class ApiConfig {

    companion object {

        private const val BASE_URL = "https://restaurant-api.dicoding.dev"
        const val BASE_IMG_URL = "https://restaurant-api.dicoding.dev/images/medium/"
        private val client = AsyncHttpClient()

        fun getListResto( responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL/list"
            return client.get(url, responseHandler)
        }

        fun getSearchResto(query: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL/search?q=$query"
            return client.get(url, responseHandler)
        }

        fun getDetailResto(id: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL/detail/$id"
            return client.get(url, responseHandler)
        }
    }
}