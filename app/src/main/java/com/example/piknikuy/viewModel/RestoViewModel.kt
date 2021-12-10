package com.example.piknikuy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.piknikuy.model.ModelResto
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class RestoViewModel: ViewModel() {
    val listResto = MutableLiveData<ArrayList<ModelResto>>()

    fun setRestoList(){
        val listItem = ArrayList<ModelResto>()
        val client = AsyncHttpClient()
        val url = "https://restaurant-api.dicoding.dev/list"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val resto = ModelResto()
                        resto.name = jsonObject.getString("name")
                        resto.city = jsonObject.getString("city")
                        resto.rating = jsonObject.getString("rating")
                        listItem.add(resto)
                        listResto.postValue(listItem)
                    }
                } catch (e: Exception) {
                    log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    402 -> "$statusCode : Forbiden"
                    403 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("error", errorMessage)
            }
        })
    }

    fun getRestoList(): LiveData<ArrayList<ModelResto>> {
        return listResto
    }

    fun setRestoDetail(id: String){
        val detailItem = ArrayList<ModelResto>()
        val client = AsyncHttpClient()
        val url = "https://restaurant-api.dicoding.dev/detail/$id"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val resto = ModelResto()
                        resto.name = jsonObject.getString("name")
                        resto.pictureId = jsonObject.getString("pictureId")
                        resto.description = jsonObject.getString("description")
                        resto.city = jsonObject.getString("city")
                        resto.rating = jsonObject.getString("rating")
                        resto.address = jsonObject.getString("address")
                        detailItem.add(resto)
                        listResto.postValue(detailItem)
                    }
                } catch (e: Exception) {
                    log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    402 -> "$statusCode : Forbiden"
                    403 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("error", errorMessage)
            }
        })
    }

    fun getRestoDetail(): LiveData<ArrayList<ModelResto>> {
        return listResto
    }
}