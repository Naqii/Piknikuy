package com.example.piknikuy.api

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestHandle

class ApiConfig {

    companion object {

        private val client = AsyncHttpClient()

        //for restaurant
        private const val BASE_URL_RESTO = "https://restaurant-api.dicoding.dev"
        const val BASE_IMG_URL_RESTO = "https://restaurant-api.dicoding.dev/images/medium/"

        fun getListResto( responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_RESTO/list"
            return client.get(url, responseHandler)
        }

        fun getSearchResto(query: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_RESTO/search?q=$query"
            return client.get(url, responseHandler)
        }

        fun getDetailResto(id: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_RESTO/detail/$id"
            return client.get(url, responseHandler)
        }

        //for hotel
        private const val BASE_URL_HOTEL = "http://1o189.mocklab.io/hotels"

        fun getListHotel( responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_HOTEL/list"
            return client.get(url, responseHandler)
        }

        fun getDetailHotel(id: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_HOTEL/detail/$id"
            return client.get(url, responseHandler)
        }

        //for wisata

    }
}