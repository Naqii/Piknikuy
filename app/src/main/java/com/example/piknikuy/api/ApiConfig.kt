package com.example.piknikuy.api

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestHandle

class ApiConfig {

    companion object {

        private val client = AsyncHttpClient()

        //for restaurant
        private const val BASE_URL_RESTO = "http://makan.mocklab.io/resto"
        fun getListResto( responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_RESTO/lists"
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
        private const val BASE_URL_WISATA = "http://destinations.mocklab.io/wisata"

        fun getListWisata( responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_WISATA/list"
            return client.get(url, responseHandler)
        }

        fun getDetailWisata(id: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$BASE_URL_WISATA/detail/$id"
            return client.get(url, responseHandler)
        }

    }
}